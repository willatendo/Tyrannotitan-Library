package tyrannotitanlib.library.compatibility.decorativeblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class DecorativeBlocksSeatBlock extends HorizontalBlock implements IWaterLoggable 
{
	protected static final VoxelShape POST_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
	protected static final VoxelShape JOIST_SHAPE_NS = Block.box(0, 4.0D, 4D, 16D, 7D, 12D);
	protected static final VoxelShape JOIST_SHAPE_EW = Block.box(4.0D, 4.0D, 0D, 12D, 7D, 16D);
	protected static final VoxelShape SEAT_SHAPE_NS = VoxelShapes.or(POST_SHAPE, JOIST_SHAPE_NS);
	protected static final VoxelShape SEAT_SHAPE_EW = VoxelShapes.or(POST_SHAPE, JOIST_SHAPE_EW);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
	public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;

	private IDecorativeBlocksWoodType woodType;

	public DecorativeBlocksSeatBlock(Properties properties, IDecorativeBlocksWoodType woodType) 
	{
		super(properties);
		this.woodType = woodType;
	}

	public IDecorativeBlocksWoodType getWoodType() 
	{
		return woodType;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) 
	{
		Direction facing = state.getValue(FACING);
		boolean attached = state.getValue(ATTACHED);
		switch (facing) 
		{
			case NORTH:
			case SOUTH:
				return (attached) ? SEAT_SHAPE_NS : JOIST_SHAPE_NS;
			case EAST:
			case WEST:
				return (attached) ? SEAT_SHAPE_EW : JOIST_SHAPE_EW;
		}
		return SEAT_SHAPE_NS;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		FluidState ifluidstate = world.getFluidState(pos);
		boolean waterloggedFlag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;
		boolean attachedFlag = isAttachablePos(world, pos);

		Direction facingDir = context.getClickedFace();
		Direction placementDir;
		if(facingDir == Direction.DOWN || facingDir == Direction.UP) 
		{
			placementDir = context.getHorizontalDirection().getOpposite();
		} 
		else 
		{
			placementDir = facingDir.getClockWise();
		}

		return this.defaultBlockState().setValue(FACING, placementDir).setValue(WATERLOGGED, Boolean.valueOf(waterloggedFlag)).setValue(OCCUPIED, Boolean.FALSE).setValue(ATTACHED, attachedFlag);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) 
	{
		if(state.getValue(WATERLOGGED)) 
		{
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}

		if(facing == Direction.DOWN) 
		{
			return state.setValue(ATTACHED, isAttachablePos(world, currentPos));
		} 
		else 
		{
			return state;
		}
	}

	private boolean isAttachablePos(IWorldReader world, BlockPos pos) 
	{
		if(world.getBlockState(pos.below()).getBlock() == Blocks.LANTERN) 
		{
			return true;
		}
		return Block.canSupportCenter(world, pos.below(), Direction.UP);
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) 
	{
		builder.add(FACING, WATERLOGGED, OCCUPIED, ATTACHED);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) 
	{
		return !state.getValue(WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) 
	{
		ItemStack heldItem = player.getItemInHand(hand);
		BlockState upperBlock = world.getBlockState(pos.above());
		boolean canSit = hit.getDirection() == Direction.UP && !state.getValue(OCCUPIED) && heldItem.isEmpty() && upperBlock.isAir(world, pos.above()) && isPlayerRange(player, pos);
		Item item = heldItem.getItem();
		boolean isSeatAttachableItem = item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof LanternBlock;
		boolean canAttachLantern = hit.getDirection() == Direction.DOWN && isSeatAttachableItem && world.getBlockState(pos.below()).isAir(world, pos.below());
		if(!world.isClientSide()) 
		{
			if(canSit) 
			{
				DecorativeBlocksDummyEntityForSitting seat = new DecorativeBlocksDummyEntityForSitting(world, pos);
				world.addFreshEntity(seat);
				player.startRiding(seat);
				return ActionResultType.SUCCESS;
			}
			else if(canAttachLantern) 
			{
				BlockState newState = state.setValue(ATTACHED, Boolean.TRUE);
				world.setBlockAndUpdate(pos, newState);
				world.sendBlockUpdated(pos, state, newState, 3);
				world.setBlock(pos.below(), (((BlockItem) item).getBlock()).defaultBlockState().setValue(BlockStateProperties.HANGING, Boolean.TRUE), 16);
				if(!player.isCreative()) 
				{
					heldItem.shrink(1);
				}
				return ActionResultType.SUCCESS;
			}
		}

		return super.use(state, world, pos, player, hand, hit);
	}

	private static boolean isPlayerRange(PlayerEntity player, BlockPos pos) 
	{
		BlockPos playerPos = player.blockPosition();
		int blockReachDistance = 2;

		if(blockReachDistance == 0)
		{	
			return playerPos.getY() - pos.getY() <= 1 && playerPos.getX() - pos.getX() == 0 && playerPos.getZ() - pos.getZ() == 0;
		}

		pos = pos.offset(0.5D, 0.5D, 0.5D);

		AxisAlignedBB range = new AxisAlignedBB(pos.getX() + blockReachDistance, pos.getY() + blockReachDistance, pos.getZ() + blockReachDistance, pos.getX() - blockReachDistance, pos.getY() - blockReachDistance, pos.getZ() - blockReachDistance);

		playerPos = playerPos.offset(0.5D, 0.5D, 0.5D);
		return range.minX <= playerPos.getX() && range.minY <= playerPos.getY() && range.minZ <= playerPos.getZ() && range.maxX >= playerPos.getX() && range.maxY >= playerPos.getY() && range.maxZ >= playerPos.getZ();
	}

	@Override
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		List<DecorativeBlocksDummyEntityForSitting> entities = world.getEntitiesOfClass(DecorativeBlocksDummyEntityForSitting.class, new AxisAlignedBB(x, y, z, x, y, z));
		for(DecorativeBlocksDummyEntityForSitting entity : entities) 
		{
			entity.remove();
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}

	@Override
	public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) 
	{
		return woodType.isFlammable();
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) 
	{
		if (woodType.isFlammable()) {
			return 20;
		} else
			return super.getFlammability(state, world, pos, face);
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) 
	{
		if(woodType.isFlammable()) 
		{
			return 5;
		} 
		else
		{
			return super.getFireSpreadSpeed(state, world, pos, face);
		}
	}
}
