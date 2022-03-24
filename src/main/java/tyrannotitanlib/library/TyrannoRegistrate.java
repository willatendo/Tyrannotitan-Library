package tyrannotitanlib.library;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tyrannotitanlib.core.content.ModUtilities;
import tyrannotitanlib.core.content.init.TyrannoItems;
import tyrannotitanlib.core.content.tags.TyrannoBlockTags;

public class TyrannoRegistrate extends AbstractRegistrate<TyrannoRegistrate> {
	protected TyrannoRegistrate(String modid) {
		super(modid);

		this.addDataGenerator(ProviderType.RECIPE, provider -> ShapedRecipeBuilder.shaped(Items.COPPER_INGOT).pattern("###").pattern("###").pattern("###").define('#', TyrannoItems.COPPER_NUGGET.get()).unlockedBy("has_item", provider.has(TyrannoItems.COPPER_NUGGET.get())).save(provider, ModUtilities.TYRANNO_UTILS.mod("copper_ingot_from_nugget")));
		
		this.addDataGenerator(ProviderType.BLOCK_TAGS, provider -> provider.tag(TyrannoBlockTags.PLANT_PLACEABLE).addTag(BlockTags.DIRT).add(Blocks.FARMLAND));
		this.addDataGenerator(ProviderType.LANG, provider -> provider.add("item.tyrannotitanlib.tyrannotitan_banner_pattern.desc", "Tyrannotitan"));
		for (DyeColor color : DyeColor.values()) {
			this.addDataGenerator(ProviderType.LANG, provider -> provider.add("block.minecraft.banner.tyrannotitan." + color.getName(), Arrays.stream(color.getName().toLowerCase(Locale.ROOT).split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" ")) + " Tyrannotitan"));
		}
	}

	public static NonNullSupplier<TyrannoRegistrate> lazy(String modid) {
		return NonNullSupplier.of(() -> new TyrannoRegistrate(modid).registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus()));
	}
}
