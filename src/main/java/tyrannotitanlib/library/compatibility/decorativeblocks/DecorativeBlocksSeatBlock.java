package tyrannotitanlib.library.compatibility.decorativeblocks;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tyrannotitanlib.library.compatibility.CompatibilityRegistries;

public class DecorativeBlocksSeatBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, WoodenBlock {
	protected static final VoxelShape POST_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D);
	protected static final VoxelShape TOP_POST = Block.box(6.0D, 7.0D, 6.0D, 10.0D, 16.0D, 10.0D);
	protected static final VoxelShape JOIST_NS = Block.box(0, 4.0D, 4D, 16D, 7D, 12D);
	protected static final VoxelShape JOIST_EW = Block.box(4.0D, 4.0D, 0D, 12D, 7D, 16D);
	protected static final VoxelShape SEAT_NS = Shapes.or(POST_SHAPE, JOIST_NS);
	protected static final VoxelShape SEAT_EW = Shapes.or(POST_SHAPE, JOIST_EW);
	protected static final VoxelShape JOIST_POST_NS = Shapes.or(TOP_POST, JOIST_NS);
	protected static final VoxelShape JOIST_POST_EW = Shapes.or(TOP_POST, JOIST_NS);
	protected static final VoxelShape SEAT_POST_NS = Shapes.or(SEAT_NS, TOP_POST);
	protected static final VoxelShape SEAT_POST_EW = Shapes.or(SEAT_EW, TOP_POST);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
	public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
	public static final BooleanProperty POST = BooleanProperty.create("post");

	private WoodType woodType;

	public DecorativeBlocksSeatBlock(Properties properties, WoodType woodType) {
		super(properties);
		this.woodType = woodType;
		this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false).setValue(OCCUPIED, false).setValue(ATTACHED, false).setValue(POST, false));
	}

	@Override
	public WoodType getWoodType() {
		return woodType;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Direction facing = state.getValue(FACING);
		boolean attached = state.getValue(ATTACHED);
		boolean post = state.getValue(POST);
		switch (facing) {
		case NORTH:
		case SOUTH:
			return (attached) ? (post ? SEAT_POST_NS : SEAT_NS) : (post ? JOIST_POST_NS : JOIST_NS);
		case EAST:
		case WEST:
			return (attached) ? (post ? SEAT_POST_EW : SEAT_EW) : (post ? JOIST_POST_EW : JOIST_EW);
		}
		return SEAT_NS;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		FluidState ifluidstate = world.getFluidState(pos);
		boolean waterloggedFlag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;
		boolean attachedFlag = isInAttachablePos(world, pos);

		Direction facingDir = context.getClickedFace();
		Direction placementDir;
		if (facingDir == Direction.DOWN || facingDir == Direction.UP) {
			placementDir = context.getHorizontalDirection().getOpposite();
		} else {
			placementDir = facingDir.getClockWise();
		}

		BlockState blockstate = this.defaultBlockState().setValue(FACING, placementDir).setValue(WATERLOGGED, waterloggedFlag).setValue(OCCUPIED, false).setValue(ATTACHED, attachedFlag);
		return blockstate;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState newstate, LevelAccessor world, BlockPos currentPos, BlockPos newpos) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		if (facing == Direction.DOWN) {
			return state.setValue(ATTACHED, isInAttachablePos(world, currentPos));
		} else {
			return state;
		}
	}

	private boolean isInAttachablePos(LevelReader world, BlockPos pos) {
		if (world.getBlockState(pos.below()).getBlock() == Blocks.LANTERN) {
			return true;
		}
		return Block.canSupportCenter(world, pos.below(), Direction.UP);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, OCCUPIED, ATTACHED, POST);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return !state.getValue(WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		ItemStack heldItem = player.getItemInHand(handIn);
		BlockState upperBlock = world.getBlockState(pos.above());
		boolean canSit = hit.getDirection() == Direction.UP && !state.getValue(OCCUPIED) && !state.getValue(POST) && heldItem.isEmpty() && upperBlock.isAir() && isPlayerInRange(player, pos);
		Item item = heldItem.getItem();
		boolean isSeatAttachableItem = item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof LanternBlock;
		boolean canAttachLantern = hit.getDirection() == Direction.DOWN && isSeatAttachableItem && world.getBlockState(pos.below()).isAir();
		if (!world.isClientSide()) {
			if (canSit) {
				DecorativeBlocksSittingEntity seat = CompatibilityRegistries.SITTING.create(world);
				seat.setSeatPos(pos);
				world.addFreshEntity(seat);
				player.startRiding(seat);
				return InteractionResult.SUCCESS;
			} else if (canAttachLantern) {
				BlockState newState = state.setValue(ATTACHED, Boolean.TRUE);
				world.setBlockAndUpdate(pos, newState);
				world.sendBlockUpdated(pos, state, newState, 3);
				world.setBlock(pos.below(), (((BlockItem) item).getBlock()).defaultBlockState().setValue(BlockStateProperties.HANGING, Boolean.TRUE), 16);
				if (!player.isCreative()) {
					heldItem.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}

		return super.use(state, world, pos, player, handIn, hit);
	}

	private static boolean isPlayerInRange(Player player, BlockPos pos) {
		BlockPos playerPos = player.blockPosition();
		int blockReachDistance = 2;

		if (blockReachDistance == 0) // player has to stand on top of the block
			return playerPos.getY() - pos.getY() <= 1 && playerPos.getX() - pos.getX() == 0 && playerPos.getZ() - pos.getZ() == 0;

		pos = pos.offset(0.5D, 0.5D, 0.5D);

		AABB range = new AABB(pos.getX() + blockReachDistance, pos.getY() + blockReachDistance, pos.getZ() + blockReachDistance, pos.getX() - blockReachDistance, pos.getY() - blockReachDistance, pos.getZ() - blockReachDistance);

		playerPos = playerPos.offset(0.5D, 0.5D, 0.5D);
		return range.minX <= playerPos.getX() && range.minY <= playerPos.getY() && range.minZ <= playerPos.getZ() && range.maxX >= playerPos.getX() && range.maxY >= playerPos.getY() && range.maxZ >= playerPos.getZ();
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		List<DecorativeBlocksSittingEntity> entities = world.getEntitiesOfClass(DecorativeBlocksSittingEntity.class, new AABB(x, y, z, x + 1, y + 1, z + 1));
		for (DecorativeBlocksSittingEntity entity : entities) {
			entity.remove(Entity.RemovalReason.DISCARDED);
		}
		super.onRemove(state, world, pos, newState, isMoving);
	}

	@Override
	public boolean isPathfindable(BlockState p_196266_1_, BlockGetter p_196266_2_, BlockPos p_196266_3_, PathComputationType p_196266_4_) {
		return false;
	}
}
