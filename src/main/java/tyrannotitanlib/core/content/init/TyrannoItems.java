package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.Util.LOG;

import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import tyrannotitanlib.tyranniregister.TyrannoRegister;

public class TyrannoItems {
	public static final Item COPPER_NUGGET = TyrannoRegister.registerItem("copper_nugget", new Item(new Properties().tab(CreativeModeTab.TAB_MATERIALS)));
	public static final Item TYRANNOTITAN_BANNER_PATTERN = TyrannoRegister.registerItem("tyrannotitan_banner_pattern", new BannerPatternItem(TyrannoBanners.TYRANNOTITAN, new Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).rarity(Rarity.EPIC)));

	public static void init() {
		LOG.debug("Registering Tyranno Items");
	}
}
