package tyrannotitanlib.content.server.init;

import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoItems 
{
	public static final Item COPPER_INGOT = TyrannoRegistries.create("copper_ingot", new Item(new Properties().tab(CreativeModeTab.TAB_MATERIALS)));
	public static final Item COPPER_NUGGET = TyrannoRegistries.create("copper_nugget", new Item(new Properties().tab(CreativeModeTab.TAB_MATERIALS)));

	public static final Item TYRANNOTITAN_BANNER_PATTERN = TyrannoRegistries.create("tyrannotitan_banner_pattern", new BannerPatternItem(TyrannoBanners.TYRANNOTITAN, new Properties().stacksTo(1).tab(CreativeModeTab.TAB_MATERIALS).rarity(Rarity.EPIC)));
	
	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Items"); }
}
