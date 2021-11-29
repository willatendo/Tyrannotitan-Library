package tyrannotitanlib.library.compatibility.decorativeblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FourWayBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class DecorativeBlocksPalisadeBlock extends FourWayBlock 
{
	private IDecorativeBlocksWoodType woodType;

	public DecorativeBlocksPalisadeBlock(Block.Properties properties, IDecorativeBlocksWoodType woodType) 
	{
		super(3.0F, 3.0F, 16.0F, 16.0F, 24.0F, properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
		this.woodType = woodType;
	}

	public IDecorativeBlocksWoodType getWoodType() 
	{
		return woodType;
	}
	
	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) 
	{
		return false;
	}

	public boolean canConnect(BlockState state, boolean flag0, Direction direction) 
	{
		Block block = state.getBlock();
		boolean flag = block instanceof DecorativeBlocksPalisadeBlock || block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
		return !isExceptionForConnection(block) && flag0 || flag;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		IBlockReader iblockreader = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
		BlockPos blockpos1 = blockpos.north();
		BlockPos blockpos2 = blockpos.east();
		BlockPos blockpos3 = blockpos.south();
		BlockPos blockpos4 = blockpos.west();
		BlockState blockstate = iblockreader.getBlockState(blockpos1);
		BlockState blockstate1 = iblockreader.getBlockState(blockpos2);
		BlockState blockstate2 = iblockreader.getBlockState(blockpos3);
		BlockState blockstate3 = iblockreader.getBlockState(blockpos4);
		return super.getStateForPlacement(context).setValue(NORTH, this.canConnect(blockstate, blockstate.isFaceSturdy(iblockreader, blockpos1, Direction.SOUTH), Direction.SOUTH)).setValue(EAST, this.canConnect(blockstate1, blockstate1.isFaceSturdy(iblockreader, blockpos2, Direction.WEST), Direction.WEST)).setValue(SOUTH, this.canConnect(blockstate2, blockstate2.isFaceSturdy(iblockreader, blockpos3, Direction.NORTH), Direction.NORTH)).setValue(WEST, Boolean.valueOf(this.canConnect(blockstate3, blockstate3.isFaceSturdy(iblockreader, blockpos4, Direction.EAST), Direction.EAST))).setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.getType() == Fluids.WATER));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState newState, IWorld world, BlockPos currentPos, BlockPos facingPos) 
	{
		if(state.getValue(WATERLOGGED)) 
		{
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? state.setValue(PROPERTY_BY_DIRECTION.get(facing), this.canConnect(newState, newState.isFaceSturdy(world, facingPos, facing.getOpposite()), facing.getOpposite())) : super.updateShape(state, facing, newState, world, currentPos, facingPos);
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) 
	{
		builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
	}

	@Override
	public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) 
	{
		return woodType.isFlammable();
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) 
	{
		if(woodType.isFlammable()) 
		{
			return 20;
		}
		else
		{
			return super.getFlammability(state, world, pos, face);
		}
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
