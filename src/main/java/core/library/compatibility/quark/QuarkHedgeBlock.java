package core.library.compatibility.quark;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class QuarkHedgeBlock extends FenceBlock {
	public static final Tag<Block> HEDGES = BlockTags.createOptional(new ResourceLocation("quark", "hedges"));
	private static final BooleanProperty EXTEND = BooleanProperty.create("extend");

	public QuarkHedgeBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(EXTEND, false));
	}

	@Override
	public boolean connectsTo(BlockState state, boolean isSideSolid, Direction direction) {
		return state.is(HEDGES);
	}

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
		return facing == Direction.UP && !state.getValue(WATERLOGGED) && plantable.getPlantType(world, pos) == PlantType.PLAINS;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(EXTEND, context.getLevel().getBlockState(context.getClickedPos().below()).is(HEDGES));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState newstate, LevelAccessor world, BlockPos pos, BlockPos newpos) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}

		if (facing == Direction.DOWN) {
			return state.setValue(EXTEND, newstate.getBlock() instanceof QuarkHedgeBlock);
		}

		return super.updateShape(state, facing, newstate, world, pos, newpos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(EXTEND);
	}
}
