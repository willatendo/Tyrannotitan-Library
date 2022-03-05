package tyrannotitanlib.library.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import tyrannotitanlib.core.content.init.TyrannoBlockEntities;

public class TyrannoChestBlockEntity extends ChestBlockEntity {
	protected TyrannoChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public TyrannoChestBlockEntity(BlockPos pos, BlockState state) {
		super(TyrannoBlockEntities.TYRANNO_CHEST.get(), pos, state);
	}

	@Override
	public AABB getRenderBoundingBox() {
		return new AABB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 2, worldPosition.getZ() + 2);
	}
}
