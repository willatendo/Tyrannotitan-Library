package tyrannotitanlib.library.base.block;

import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class TyrannoPillarBlock extends Block implements IWaterLoggable
{
	public static final VoxelShape BASE = Stream.of(Block.box(13, 0, 3, 16, 16, 13), Block.box(0, 0, 0, 16, 16, 3), Block.box(0, 0, 13, 16, 16, 16), Block.box(0, 0, 3, 3, 16, 13)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
	public static final VoxelShape MID = Stream.of(Block.box(1, 0, 1, 15, 16, 3), Block.box(1, 0, 13, 15, 16, 15), Block.box(1, 0, 3, 3, 16, 13), Block.box(13, 0, 3, 15, 16, 13)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
	public static final VoxelShape TOP = Stream.of(Block.box(13, 11, 3, 16, 13, 13), Block.box(1, 0, 1, 15, 11, 3), Block.box(1, 0, 13, 15, 11, 15), Block.box(1, 0, 3, 3, 11, 13), Block.box(13, 0, 3, 15, 11, 13), Block.box(0, 11, 0, 16, 13, 3), Block.box(0, 11, 13, 16, 13, 16), Block.box(0, 11, 3, 3, 13, 13)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	
	public TyrannoPillarBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(UP, false).setValue(DOWN, false).setValue(WATERLOGGED, false));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) 
	{
		boolean up = state.getValue(UP);
		boolean down = state.getValue(DOWN);
		if(!up) 
		{
			if(!down) 
			{
				return BASE;
			} 
			else 
			{
				return TOP;
			}
		} 
		else 
		{
			if(!down) 
			{
				return BASE;
			} 
			else 
			{
				return MID;
			}
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		World world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		boolean flag = world.getFluidState(pos).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(UP, canConnect(world.getBlockState(pos.above()), pos, world, Direction.UP)).setValue(DOWN, canConnect(world.getBlockState(pos.below()), pos, world, Direction.DOWN));
	}

	public static boolean canConnect(BlockState state, BlockPos pos, IWorld world, Direction dir) 
	{
		if(state.getBlock() instanceof TyrannoPillarBlock) 
		{
			return true;
		}
		return false;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState newstate, IWorld world, BlockPos pos, BlockPos newpos) 
	{
		if(state.getValue(WATERLOGGED)) 
		{
			world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}

		if(direction == Direction.UP) 
		{
			return state.setValue(UP, canConnect(newstate, pos, world, direction));
		} 
		else if(direction == Direction.DOWN) 
		{
			return state.setValue(DOWN, canConnect(newstate, pos, world, direction));
		}
		return state;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(UP, DOWN, WATERLOGGED);
	}
}
