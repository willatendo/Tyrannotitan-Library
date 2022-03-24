package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.ModUtilities.LOG;

import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import tyrannotitanlib.TyrannotitanLibrary;
import tyrannotitanlib.library.TyrannoRegistrate;

public class TyrannoItems {
	public static final TyrannoRegistrate REGISTRATE = TyrannotitanLibrary.CENTRAL_REGISTRATE.get().creativeModeTab(() -> CreativeModeTab.TAB_MISC);

	public static final ItemEntry<Item> COPPER_NUGGET = REGISTRATE.item("copper_nugget", Item::new).recipe((item, recipeProvider) -> ShapelessRecipeBuilder.shapeless(item.get()).requires(Ingredient.of(Items.COPPER_INGOT)).unlockedBy("has_item", recipeProvider.has(Items.COPPER_INGOT)).save(recipeProvider)).register();
	public static final ItemEntry<BannerPatternItem> TYRANNOTITAN_BANNER_PATTERN = REGISTRATE.item("tyrannotitan_banner_pattern", properties ->  new BannerPatternItem(TyrannoBanners.TYRANNOTITAN, properties)).properties(properties -> properties.stacksTo(1).rarity(Rarity.EPIC)).recipe((item, recipeProvider) -> ShapelessRecipeBuilder.shapeless(item.get()).requires(Ingredient.of(Items.PAPER)).requires(Ingredient.of(Items.DRAGON_BREATH)).unlockedBy("has_item", recipeProvider.has(Items.DRAGON_BREATH)).save(recipeProvider)).register();

	public static void init() {
		LOG.debug("Registering Tyranno Items");
	}
}
