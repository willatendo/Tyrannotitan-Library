package tyrannotitanlib.library.tyrannotitanlib.block;

import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TyrannoPillarBlock extends Block implements SimpleWaterloggedBlock {
	public static final VoxelShape BASE = Stream.of(Block.box(13, 0, 3, 16, 16, 13), Block.box(0, 0, 0, 16, 16, 3), Block.box(0, 0, 13, 16, 16, 16), Block.box(0, 0, 3, 3, 16, 13)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	public static final VoxelShape MID = Stream.of(Block.box(1, 0, 1, 15, 16, 3), Block.box(1, 0, 13, 15, 16, 15), Block.box(1, 0, 3, 3, 16, 13), Block.box(13, 0, 3, 15, 16, 13)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	public static final VoxelShape TOP = Stream.of(Block.box(13, 11, 3, 16, 13, 13), Block.box(1, 0, 1, 15, 11, 3), Block.box(1, 0, 13, 15, 11, 15), Block.box(1, 0, 3, 3, 11, 13), Block.box(13, 0, 3, 15, 11, 13), Block.box(0, 11, 0, 16, 13, 3), Block.box(0, 11, 13, 16, 13, 16), Block.box(0, 11, 3, 3, 13, 13)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

	public TyrannoPillarBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(UP, false).setValue(DOWN, false).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		boolean up = state.getValue(UP);
		boolean down = state.getValue(DOWN);
		if (!up) {
			if (!down) {
				return BASE;
			} else {
				return TOP;
			}
		} else {
			if (!down) {
				return BASE;
			} else {
				return MID;
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		boolean flag = world.getFluidState(pos).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(UP, canConnect(world.getBlockState(pos.above()), pos, world, Direction.UP)).setValue(DOWN, canConnect(world.getBlockState(pos.below()), pos, world, Direction.DOWN));
	}

	public static boolean canConnect(BlockState state, BlockPos pos, LevelAccessor world, Direction dir) {
		if (state.getBlock() instanceof TyrannoPillarBlock) {
			return true;
		}
		return false;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState newstate, LevelAccessor world, BlockPos pos, BlockPos newpos) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}

		if (direction == Direction.UP) {
			return state.setValue(UP, canConnect(newstate, pos, world, direction));
		} else if (direction == Direction.DOWN) {
			return state.setValue(DOWN, canConnect(newstate, pos, world, direction));
		}
		return state;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(UP, DOWN, WATERLOGGED);
	}
}
