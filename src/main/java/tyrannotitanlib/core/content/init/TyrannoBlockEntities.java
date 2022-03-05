package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.Util.LOG;
import static tyrannotitanlib.core.content.Util.collectBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
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
	public static final RegistryObject<BlockEntityType<TyrannoChestBlockEntity>> TYRANNO_CHEST = TyrannoRegister.BLOCK_ENTITIES.register("tyranno_chest", () -> BlockEntityType.Builder.of(TyrannoChestBlockEntity::new, collectBlocks(TyrannoChestBlock.class)).build(null));
	public static final RegistryObject<BlockEntityType<TyrannoTrappedChestBlockEntity>> TYRANNO_TRAPPED_CHEST = TyrannoRegister.BLOCK_ENTITIES.register("tyranno_trapped_chest", () -> BlockEntityType.Builder.of(TyrannoTrappedChestBlockEntity::new, collectBlocks(TyrannoTrappedChestBlock.class)).build(null));
	public static final RegistryObject<BlockEntityType<TyrannoBeehiveBlockEntity>> TYRANNO_BEEHIVE = TyrannoRegister.BLOCK_ENTITIES.register("tyranno_beehive", () -> BlockEntityType.Builder.of(TyrannoBeehiveBlockEntity::new, collectBlocks(TyrannoBeehiveBlock.class)).build(null));
	public static final RegistryObject<BlockEntityType<TyrannoBarrelBlockEntity>> TYRANNO_BARREL = TyrannoRegister.BLOCK_ENTITIES.register("tyranno_barrel", () -> BlockEntityType.Builder.of(TyrannoBarrelBlockEntity::new, collectBlocks(TyrannoBarrelBlock.class)).build(null));

	public static void init() {
		LOG.debug("Registering Tyranno Block Entities");
	}
}
