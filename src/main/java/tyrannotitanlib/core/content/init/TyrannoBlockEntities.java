package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.Util.LOG;
import static tyrannotitanlib.core.content.Util.collectBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import tyrannotitanlib.library.block.TyrannoBarrelBlock;
import tyrannotitanlib.library.block.TyrannoBeehiveBlock;
import tyrannotitanlib.library.block.TyrannoChestBlock;
import tyrannotitanlib.library.block.TyrannoTrappedChestBlock;
import tyrannotitanlib.library.block.entity.TyrannoBarrelBlockEntity;
import tyrannotitanlib.library.block.entity.TyrannoBeehiveBlockEntity;
import tyrannotitanlib.library.block.entity.TyrannoChestBlockEntity;
import tyrannotitanlib.library.block.entity.TyrannoTrappedChestBlockEntity;
import tyrannotitanlib.tyranniregister.TyrannoRegister;

public class TyrannoBlockEntities {
	public static final BlockEntityType<TyrannoChestBlockEntity> CHEST_BLOCK_ENTITY = TyrannoRegister.registerBlockEntity("chest_block_entity", BlockEntityType.Builder.of(TyrannoChestBlockEntity::new, collectBlocks(TyrannoChestBlock.class)).build(null));
	public static final BlockEntityType<TyrannoTrappedChestBlockEntity> TRAPPED_CHEST_BLOCK_ENTITY = TyrannoRegister.registerBlockEntity("trapped_chest_block_entity", BlockEntityType.Builder.of(TyrannoTrappedChestBlockEntity::new, collectBlocks(TyrannoTrappedChestBlock.class)).build(null));
	public static final BlockEntityType<TyrannoBeehiveBlockEntity> BEEHIVE_BLOCK_ENTITY = TyrannoRegister.registerBlockEntity("beehive_block_entity", BlockEntityType.Builder.of(TyrannoBeehiveBlockEntity::new, collectBlocks(TyrannoBeehiveBlock.class)).build(null));
	public static final BlockEntityType<TyrannoBarrelBlockEntity> BARREL_BLOCK_ENTITY = TyrannoRegister.registerBlockEntity("barrel_block_entity", BlockEntityType.Builder.of(TyrannoBarrelBlockEntity::new, collectBlocks(TyrannoBarrelBlock.class)).build(null));

	public static void init() {
		LOG.debug("Registering Tyranno Block Entities");
	}
}
