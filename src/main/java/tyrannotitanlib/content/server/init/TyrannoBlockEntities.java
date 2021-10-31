package tyrannotitanlib.content.server.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.block.ITyrannoSign;
import tyrannotitanlib.library.base.block.TyrannoBarrelBlock;
import tyrannotitanlib.library.base.block.TyrannoChestBlock;
import tyrannotitanlib.library.base.block.TyrannoTrappedChestBlock;
import tyrannotitanlib.library.base.block.entity.TyrannoBarrelBlockEntity;
import tyrannotitanlib.library.base.block.entity.TyrannoBeehiveBlockEntity;
import tyrannotitanlib.library.base.block.entity.TyrannoChestBlockEntity;
import tyrannotitanlib.library.base.block.entity.TyrannoSignBlockEntity;
import tyrannotitanlib.library.base.block.entity.TyrannoTrappedChestBlockEntity;
import tyrannotitanlib.library.compatibility.charm.CharmCrateBlock;
import tyrannotitanlib.library.compatibility.charm.CharmCrateBlockEntity;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoBlockEntities 
{		
	public static final TileEntityType<TyrannoSignBlockEntity> SIGN_BLOCK_ENTITY = TyrannoRegistries.create("sign_block_entity", TileEntityType.Builder.of(TyrannoSignBlockEntity::new, collectBlocks(ITyrannoSign.class)).build(null));	
	public static final TileEntityType<TyrannoChestBlockEntity> CHEST_BLOCK_ENTITY = TyrannoRegistries.create("chest_block_entity", TileEntityType.Builder.of(TyrannoChestBlockEntity::new, collectBlocks(TyrannoChestBlock.class)).build(null));
	public static final TileEntityType<TyrannoTrappedChestBlockEntity> TRAPPED_CHEST_BLOCK_ENTITY = TyrannoRegistries.create("trapped_chest_block_entity", TileEntityType.Builder.of(TyrannoTrappedChestBlockEntity::new, collectBlocks(TyrannoTrappedChestBlock.class)).build(null));
	public static final TileEntityType<TyrannoBeehiveBlockEntity> BEEHIVE_BLOCK_ENTITY = TyrannoRegistries.create("beehive_block_entity", TileEntityType.Builder.of(TyrannoBeehiveBlockEntity::new, collectBlocks(ITyrannoSign.class)).build(null));	
	public static final TileEntityType<TyrannoBarrelBlockEntity> BARREL_BLOCK_ENTITY = TyrannoRegistries.create("barrel_block_entity", TileEntityType.Builder.of(TyrannoBarrelBlockEntity::new, collectBlocks(TyrannoBarrelBlock.class)).build(null));
	public static final TileEntityType<CharmCrateBlockEntity> CRATE_BLOCK_ENTITY = TyrannoRegistries.create("crate_block_entity", TileEntityType.Builder.of(CharmCrateBlockEntity::new, collectBlocks(CharmCrateBlock.class)).build(null));

	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Tile Entities"); }

	public static Block[] collectBlocks(Class<?> blockClass) 
	{
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
}
	