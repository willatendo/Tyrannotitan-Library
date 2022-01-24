package tyrannotitanlib.library.compatibility.decorativeblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DecorativeBlocksSupportBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
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
	public static final VoxelShape NORTH_SHAPE = Shapes.or(TOP, NORTH_PART);
	public static final VoxelShape SOUTH_SHAPE = Shapes.or(TOP, SOUTH_PART);
	public static final VoxelShape EAST_SHAPE = Shapes.or(TOP, EAST_PART);
	public static final VoxelShape WEST_SHAPE = Shapes.or(TOP, WEST_PART);
	public static final VoxelShape NORTH_DOWN_SHAPE = Shapes.or(BOTTOM, NORTH_PART);
	public static final VoxelShape SOUTH_DOWN_SHAPE = Shapes.or(BOTTOM, SOUTH_PART);
	public static final VoxelShape EAST_DOWN_SHAPE = Shapes.or(BOTTOM, EAST_PART);
	public static final VoxelShape WEST_DOWN_SHAPE = Shapes.or(BOTTOM, WEST_PART);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty UP = BlockStateProperties.UP;

	private IDecorativeBlocksWoodType woodType;

	public DecorativeBlocksSupportBlock(Block.Properties properties, IDecorativeBlocksWoodType woodType) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(UP, Boolean.TRUE));
		this.woodType = woodType;
	}

	public IDecorativeBlocksWoodType getWoodType() {
		return woodType;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		Direction facing = state.getValue(FACING);
		if (state.getValue(UP)) {
			switch (facing) {
			case NORTH:
				return NORTH_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case EAST:
				return EAST_SHAPE;
			case WEST:
				return WEST_SHAPE;
			}
		} else {
			switch (facing) {
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
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
		boolean flag = ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8;

		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, UP);
	}

	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return !state.getValue(WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return woodType.isFlammable();
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		if (woodType.isFlammable()) {
			return 20;
		} else {
			return super.getFlammability(state, world, pos, face);
		}
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		if (woodType.isFlammable()) {
			return 5;
		} else {
			return super.getFireSpreadSpeed(state, world, pos, face);
		}
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType path) {
		return false;
	}
}
