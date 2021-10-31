package tyrannotitanlib.library.compatibility.decorativeblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class DecorativeBlocksSupportBlock extends HorizontalBlock implements IWaterLoggable 
{
	private static final double d0 = 3D;
	private static final double d1 = 13D;
	private static final double d2 = 4D;
	private static final double d3 = 12D;
	public static final VoxelShape TOP = Block.box(0, d1, 0, 16D, 16D, 16D);
	public static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16D, 16D - d1, 16D);
	public static final VoxelShape NORTH_PART = Block.box(d2, 0, d1, d3, d1, 16D);
	public static final VoxelShape SOUTH_PART = Block.box(d2, 0, 0, d3, d1, d0);
	public static final VoxelShape EAST_PART = Block.box(0, 0, d2, d0, d1, d3);
	public static final VoxelShape WEST_PART = Block.box(d1, 0, d2, 16D, d1, d3);
	public static final VoxelShape NORTH_SHAPE = VoxelShapes.or(TOP, NORTH_PART);
	public static final VoxelShape SOUTH_SHAPE = VoxelShapes.or(TOP, SOUTH_PART);
	public static final VoxelShape EAST_SHAPE = VoxelShapes.or(TOP, EAST_PART);
	public static final VoxelShape WEST_SHAPE = VoxelShapes.or(TOP, WEST_PART);
	public static final VoxelShape NORTH_DOWN_SHAPE = VoxelShapes.or(BOTTOM, NORTH_PART);
	public static final VoxelShape SOUTH_DOWN_SHAPE = VoxelShapes.or(BOTTOM, SOUTH_PART);
	public static final VoxelShape EAST_DOWN_SHAPE = VoxelShapes.or(BOTTOM, EAST_PART);
	public static final VoxelShape WEST_DOWN_SHAPE = VoxelShapes.or(BOTTOM, WEST_PART);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty UP = BlockStateProperties.UP;

	private IDecorativeBlocksWoodType woodType;

	public DecorativeBlocksSupportBlock(Block.Properties properties, IDecorativeBlocksWoodType woodType) 
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(UP, Boolean.TRUE));
		this.woodType = woodType;
	}

	public IDecorativeBlocksWoodType getWoodType() 
	{
		return woodType;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		Direction facing = state.getValue(FACING);
		if(state.getValue(UP)) 
		{
			switch(facing) 
			{
				case NORTH:
					return NORTH_SHAPE;
				case SOUTH:
					return SOUTH_SHAPE;
				case EAST:
					return EAST_SHAPE;
				case WEST:
					return WEST_SHAPE;
			}
		}
		else 
		{
			switch(facing) 
			{
				case NORTH:
					return NORTH_SHAPE;
				case SOUTH:
					return SOUTH_SHAPE;
				case EAST:
					return EAST_SHAPE;
				case WEST:
					return WEST_SHAPE;
			}
		}
		return NORTH_SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;

		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) 
	{
		builder.add(FACING, WATERLOGGED, UP);
	}

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

	@Override
	public boolean isPathfindable(BlockState state, IBlockReader reader, BlockPos pos, PathType path) 
	{
		return false;
	}
}
