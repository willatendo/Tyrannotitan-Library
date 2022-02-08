package core.content.init;

import static core.content.Util.LOG;

import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;

public class TyrannoItems {
	public static final Item TYRANNOTITAN_BANNER_PATTERN = TyrannoRegistries.create("tyrannotitan_banner_pattern", new BannerPatternItem(TyrannoBanners.TYRANNOTITAN, new Properties().stacksTo(1).tab(CreativeModeTab.TAB_MATERIALS).rarity(Rarity.EPIC)));

	public static void init() {
		LOG.debug("Registering Tyranno Items");
	}
}
