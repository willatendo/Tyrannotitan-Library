package tyrannotitanlib.library.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.core.content.init.TyrannoBlockEntities;

public class TyrannoBarrelBlockEntity extends BarrelBlockEntity {
	public TyrannoBarrelBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}
	public TyrannoBarrelBlockEntity(BlockEntityType<? extends TyrannoBarrelBlockEntity> entity, BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return TyrannoBlockEntities.TYRANNO_BARREL.get();
	}
}
