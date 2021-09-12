package tyrannotitanlib.content.server.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.content.TyrannotitanLibrary.Registry;
import tyrannotitanlib.library.base.block.ITyrannoSign;
import tyrannotitanlib.library.base.tileentity.TyrannoSignTileEntity;

public class TyrannoTileEntities 
{		
	public static final TileEntityType<TyrannoSignTileEntity> SIGN = Registry.create("sign", TileEntityType.Builder.of(TyrannoSignTileEntity::new, collectBlocks(ITyrannoSign.class)).build(null));	

	public static Block[] collectBlocks(Class<?> blockClass) 
	{
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
	public static void init() { }
}
	