package tyrannotitanlib.library.tyrannotitanlib.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class TyrannoBarrelBlockEntity extends BarrelBlockEntity {
	public TyrannoBarrelBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return TyrannoBlockEntities.BARREL_BLOCK_ENTITY;
	}
}
