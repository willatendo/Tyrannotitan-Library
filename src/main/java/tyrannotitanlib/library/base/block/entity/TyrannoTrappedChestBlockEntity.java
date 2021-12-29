package tyrannotitanlib.library.base.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class TyrannoTrappedChestBlockEntity extends TyrannoChestBlockEntity {
	public TyrannoTrappedChestBlockEntity(BlockPos pos, BlockState state) {
		super(TyrannoBlockEntities.TRAPPED_CHEST_BLOCK_ENTITY, pos, state);
	}

	@Override
	protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int i1, int i2) {
		super.signalOpenCount(level, pos, state, i1, i2);
		if (i1 != i2) {
			Block block = state.getBlock();
			level.updateNeighborsAt(pos, block);
			level.updateNeighborsAt(pos.below(), block);
		}
	}
}
