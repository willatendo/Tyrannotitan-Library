package tyrannotitanlib.library.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public interface SimpleLavaloddedBlock extends BucketPickup, LiquidBlockContainer {
	@Override
	default boolean canPlaceLiquid(BlockGetter reader, BlockPos pos, BlockState state, Fluid fluid) {
		return !state.getValue(BooleanProperty.create("lavalogged")) && fluid == Fluids.LAVA;
	}

	@Override
	default boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
		if (!state.getValue(TyrannoBlockStateProperties.LAVALOGGED) && fluid.getType() == Fluids.LAVA) {
			if (!world.isClientSide()) {
				world.setBlock(pos, state.setValue(TyrannoBlockStateProperties.LAVALOGGED, Boolean.valueOf(true)), 3);
				world.scheduleTick(pos, fluid.getType(), fluid.getType().getTickDelay(world));
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	default ItemStack pickupBlock(LevelAccessor world, BlockPos pos, BlockState state) {
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			world.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)), 3);
			if (!state.canSurvive(world, pos)) {
				world.destroyBlock(pos, true);
			}

			return new ItemStack(Items.LAVA_BUCKET);
		} else {
			return ItemStack.EMPTY;
		}
	}
}
