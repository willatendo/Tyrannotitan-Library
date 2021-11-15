package tyrannotitanlib.content.server.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item.Properties;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoItems 
{
	public static final Item COPPER_INGOT = TyrannoRegistries.create("copper_ingot", new Item(new Properties().tab(ItemGroup.TAB_MATERIALS)));
	public static final Item COPPER_NUGGET = TyrannoRegistries.create("copper_nugget", new Item(new Properties().tab(ItemGroup.TAB_MATERIALS)));

	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Items"); }
}
