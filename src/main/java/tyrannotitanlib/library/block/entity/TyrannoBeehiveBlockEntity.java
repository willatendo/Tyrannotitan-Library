package tyrannotitanlib.library.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.core.content.init.TyrannoBlockEntities;

public class TyrannoBeehiveBlockEntity extends BeehiveBlockEntity {
	public TyrannoBeehiveBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return TyrannoBlockEntities.BEEHIVE_BLOCK_ENTITY;
	}
}
