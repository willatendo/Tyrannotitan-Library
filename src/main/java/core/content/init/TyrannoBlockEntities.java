package core.content.init;

import static core.content.Util.LOG;
import static core.content.Util.collectBlocks;

import core.library.tyrannotitanlib.block.TyrannoBarrelBlock;
import core.library.tyrannotitanlib.block.TyrannoBeehiveBlock;
import core.library.tyrannotitanlib.block.TyrannoChestBlock;
import core.library.tyrannotitanlib.block.TyrannoTrappedChestBlock;
import core.library.tyrannotitanlib.block.entity.TyrannoBarrelBlockEntity;
import core.library.tyrannotitanlib.block.entity.TyrannoBeehiveBlockEntity;
import core.library.tyrannotitanlib.block.entity.TyrannoChestBlockEntity;
import core.library.tyrannotitanlib.block.entity.TyrannoTrappedChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TyrannoBlockEntities {
	public static final BlockEntityType<TyrannoChestBlockEntity> CHEST_BLOCK_ENTITY = TyrannoRegistries.create("chest_block_entity", BlockEntityType.Builder.of(TyrannoChestBlockEntity::new, collectBlocks(TyrannoChestBlock.class)).build(null));
	public static final BlockEntityType<TyrannoTrappedChestBlockEntity> TRAPPED_CHEST_BLOCK_ENTITY = TyrannoRegistries.create("trapped_chest_block_entity", BlockEntityType.Builder.of(TyrannoTrappedChestBlockEntity::new, collectBlocks(TyrannoTrappedChestBlock.class)).build(null));
	public static final BlockEntityType<TyrannoBeehiveBlockEntity> BEEHIVE_BLOCK_ENTITY = TyrannoRegistries.create("beehive_block_entity", BlockEntityType.Builder.of(TyrannoBeehiveBlockEntity::new, collectBlocks(TyrannoBeehiveBlock.class)).build(null));
	public static final BlockEntityType<TyrannoBarrelBlockEntity> BARREL_BLOCK_ENTITY = TyrannoRegistries.create("barrel_block_entity", BlockEntityType.Builder.of(TyrannoBarrelBlockEntity::new, collectBlocks(TyrannoBarrelBlock.class)).build(null));

	public static void init() {
		LOG.debug("Registering Tyranno Block Entities");
	}
}
