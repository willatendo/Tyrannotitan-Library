package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.ModUtilities.LOG;

import com.tterrag.registrate.util.entry.BlockEntityEntry;

import tyrannotitanlib.TyrannotitanLibrary;
import tyrannotitanlib.core.client.chest.TyrannoChestBlockEntityRender;
import tyrannotitanlib.library.TyrannoRegistrate;
import tyrannotitanlib.library.block.entity.TyrannoBarrelBlockEntity;
import tyrannotitanlib.library.block.entity.TyrannoBeehiveBlockEntity;
import tyrannotitanlib.library.block.entity.TyrannoChestBlockEntity;
import tyrannotitanlib.library.block.entity.TyrannoTrappedChestBlockEntity;

public class TyrannoBlockEntities {
	// import static tyrannotitanlib.core.content.ModUtilities.collectBlocks;
	public static final TyrannoRegistrate REGISTRATE = TyrannotitanLibrary.CENTRAL_REGISTRATE.get();

	public static final BlockEntityEntry<TyrannoChestBlockEntity> TYRANNO_CHEST = REGISTRATE.<TyrannoChestBlockEntity>blockEntity("tyranno_chest", TyrannoChestBlockEntity::new).renderer(() -> TyrannoChestBlockEntityRender::new).register();
	public static final BlockEntityEntry<TyrannoTrappedChestBlockEntity> TYRANNO_TRAPPED_CHEST = REGISTRATE.<TyrannoTrappedChestBlockEntity>blockEntity("tyranno_trapped_chest", TyrannoTrappedChestBlockEntity::new).renderer(() -> TyrannoChestBlockEntityRender::new).register();
	public static final BlockEntityEntry<TyrannoBeehiveBlockEntity> TYRANNO_BEEHIVE = REGISTRATE.<TyrannoBeehiveBlockEntity>blockEntity("tyranno_beehive", TyrannoBeehiveBlockEntity::new).register();
	public static final BlockEntityEntry<TyrannoBarrelBlockEntity> TYRANNO_BARREL = REGISTRATE.<TyrannoBarrelBlockEntity>blockEntity("tyranno_barrel", TyrannoBarrelBlockEntity::new).register();

	public static void init() {
		LOG.debug("Registering Tyranno Block Entities");
	}
}
