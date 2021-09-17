package tyrannotitanlib.content.server.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.content.server.util.TyrannoRegistries;
import tyrannotitanlib.library.base.block.ITyrannoSign;
import tyrannotitanlib.library.base.tileentity.TyrannoSignTileEntity;
import tyrannotitanlib.library.base.util.TyrannoUtils;

public class TyrannoTileEntities 
{		
	public static final TileEntityType<TyrannoSignTileEntity> SIGN_TILE_ENTITY = TyrannoRegistries.create("sign_tile_entity", TileEntityType.Builder.of(TyrannoSignTileEntity::new, collectBlocks(ITyrannoSign.class)).build(null));	

	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Tile Entities"); }

	public static Block[] collectBlocks(Class<?> blockClass) 
	{
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
}
	