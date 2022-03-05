package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.Util.LOG;

import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;
import tyrannotitanlib.tyranniregister.TyrannoRegister;

public class TyrannoItems {
	public static final RegistryObject<Item> COPPER_NUGGET = TyrannoRegister.ITEMS.register("copper_nugget", () -> new Item(new Properties().tab(CreativeModeTab.TAB_MATERIALS)));
	public static final RegistryObject<BannerPatternItem> TYRANNOTITAN_BANNER_PATTERN = TyrannoRegister.ITEMS.register("tyrannotitan_banner_pattern", () -> new BannerPatternItem(TyrannoBanners.TYRANNOTITAN, new Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC).rarity(Rarity.EPIC)));

	public static void init() {
		LOG.debug("Registering Tyranno Items");
	}
}
