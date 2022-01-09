package tyrannotitanlib.content.server.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.block.TyrannoBarrelBlock;
import tyrannotitanlib.library.base.block.TyrannoBeehiveBlock;
import tyrannotitanlib.library.base.block.TyrannoChestBlock;
import tyrannotitanlib.library.base.block.TyrannoTrappedChestBlock;
import tyrannotitanlib.library.base.block.entity.TyrannoBarrelBlockEntity;
import tyrannotitanlib.library.base.block.entity.TyrannoBeehiveBlockEntity;
import tyrannotitanlib.library.base.block.entity.TyrannoChestBlockEntity;
import tyrannotitanlib.library.base.block.entity.TyrannoTrappedChestBlockEntity;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoBlockEntities {
	public static final BlockEntityType<TyrannoChestBlockEntity> CHEST_BLOCK_ENTITY = TyrannoRegistries.create("chest_block_entity", BlockEntityType.Builder.of(TyrannoChestBlockEntity::new, collectBlocks(TyrannoChestBlock.class)).build(null));
	public static final BlockEntityType<TyrannoTrappedChestBlockEntity> TRAPPED_CHEST_BLOCK_ENTITY = TyrannoRegistries.create("trapped_chest_block_entity", BlockEntityType.Builder.of(TyrannoTrappedChestBlockEntity::new, collectBlocks(TyrannoTrappedChestBlock.class)).build(null));
	public static final BlockEntityType<TyrannoBeehiveBlockEntity> BEEHIVE_BLOCK_ENTITY = TyrannoRegistries.create("beehive_block_entity", BlockEntityType.Builder.of(TyrannoBeehiveBlockEntity::new, collectBlocks(TyrannoBeehiveBlock.class)).build(null));
	public static final BlockEntityType<TyrannoBarrelBlockEntity> BARREL_BLOCK_ENTITY = TyrannoRegistries.create("barrel_block_entity", BlockEntityType.Builder.of(TyrannoBarrelBlockEntity::new, collectBlocks(TyrannoBarrelBlock.class)).build(null));
	// public static final BlockEntityType<CharmCrateBlockEntity> CRATE_BLOCK_ENTITY = TyrannoRegistries.create("crate_block_entity",
	// BlockEntityType.Builder.of(CharmCrateBlockEntity::new, collectBlocks(CharmCrateBlock.class)).build(null));

	public static void init() {
		TyrannoUtils.LOGGER.debug("Registering Tyranno Block Entities");
	}

	public static Block[] collectBlocks(Class<?> blockClass) {
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
}
