package tyrannotitanlib.content.server.init;

import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import tyrannotitanlib.library.base.item.TyrannoBoatItem;
import tyrannotitanlib.library.base.utils.TyrannoBoatRegistry;
import tyrannotitanlib.library.base.utils.TyrannoUtils;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;

public class TyrannoItems 
{		
	public static final Item BOAT = register();
			
	public static Item register()
	{
		Item item = new TyrannoBoatItem("tyrannotitanlib:test", new Properties().tab(ItemGroup.TAB_MISC));
		TyrannoRegister.registerItem("test_boat", item);
		TyrannoBoatRegistry.registerBoat("tyrannotitanlib:test", item, Blocks.ACACIA_DOOR);
		return item;
	}
	
	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Items"); }
}
