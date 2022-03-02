package tyrannotitanlib.library.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.core.content.init.TyrannoBlockEntities;
import tyrannotitanlib.library.block.entity.TyrannoChestBlockEntity;

public class TyrannoChestBlock extends ChestBlock implements TyrannoChest {
	private final String type;

	public TyrannoChestBlock(String type, Properties properties) {
		super(properties, () -> TyrannoBlockEntities.CHEST_BLOCK_ENTITY);
		this.type = type;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TyrannoChestBlockEntity(pos, state);
	}

	@Override
	public String getChestType() {
		return this.type;
	}
}
