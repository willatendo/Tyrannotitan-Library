package tyrannotitanlib.content.server.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.content.server.util.TyrannoRegistries;
import tyrannotitanlib.library.base.block.ITyrannoSign;
import tyrannotitanlib.library.base.block.TyrannoBarrelBlock;
import tyrannotitanlib.library.base.block.TyrannoChestBlock;
import tyrannotitanlib.library.base.block.TyrannoTrappedChestBlock;
import tyrannotitanlib.library.base.tileentity.TyrannoBarrelTileEntity;
import tyrannotitanlib.library.base.tileentity.TyrannoBeehiveTileEntity;
import tyrannotitanlib.library.base.tileentity.TyrannoChestTileEntity;
import tyrannotitanlib.library.base.tileentity.TyrannoSignTileEntity;
import tyrannotitanlib.library.base.tileentity.TyrannoTrappedChestTileEntity;
import tyrannotitanlib.library.base.utils.TyrannoUtils;

public class TyrannoTileEntities 
{		
	public static final TileEntityType<TyrannoSignTileEntity> SIGN_TILE_ENTITY = TyrannoRegistries.create("sign_tile_entity", TileEntityType.Builder.of(TyrannoSignTileEntity::new, collectBlocks(ITyrannoSign.class)).build(null));	
	public static final TileEntityType<TyrannoChestTileEntity> CHEST_TILE_ENTITY = TyrannoRegistries.create("chest_tile_entity", TileEntityType.Builder.of(TyrannoChestTileEntity::new, collectBlocks(TyrannoChestBlock.class)).build(null));
	public static final TileEntityType<TyrannoTrappedChestTileEntity> TRAPPED_CHEST_TILE_ENTITY = TyrannoRegistries.create("trapped_chest_tile_entity", TileEntityType.Builder.of(TyrannoTrappedChestTileEntity::new, collectBlocks(TyrannoTrappedChestBlock.class)).build(null));
	public static final TileEntityType<TyrannoBeehiveTileEntity> BEEHIVE_TILE_ENTITY = TyrannoRegistries.create("beehive_tile_entity", TileEntityType.Builder.of(TyrannoBeehiveTileEntity::new, collectBlocks(ITyrannoSign.class)).build(null));	
	public static final TileEntityType<TyrannoBarrelTileEntity> BARREL_TILE_ENTITY = TyrannoRegistries.create("barrel_tile_entity", TileEntityType.Builder.of(TyrannoBarrelTileEntity::new, collectBlocks(TyrannoBarrelBlock.class)).build(null));

	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Tile Entities"); }

	public static Block[] collectBlocks(Class<?> blockClass) 
	{
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
}
	