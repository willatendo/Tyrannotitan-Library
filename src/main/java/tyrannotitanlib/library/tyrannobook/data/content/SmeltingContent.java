package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.ImmutableList;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.element.ImageData;
import tyrannotitanlib.library.tyrannobook.data.element.IngredientData;
import tyrannotitanlib.library.tyrannobook.data.element.TextData;
import tyrannotitanlib.library.tyrannobook.screen.book.BookScreen;
import tyrannotitanlib.library.tyrannobook.screen.book.BookTextures;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.ImageElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.ItemElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.TextElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.TooltipElement;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class SmeltingContent extends PageContent {
	public static final ResourceLocation ID = TyrannoUtils.rL("smelting");

	private static final NonNullList<ItemStack> FUELS;

	public static final transient int TEX_SIZE = 128;
	public static final transient ImageData IMG_SMELTING = new ImageData(BookTextures.SMELTING_TEXTURE, 0, 0, 110, 114, TEX_SIZE, TEX_SIZE);

	public static final transient int INPUT_X = 5;
	public static final transient int INPUT_Y = 5;
	public static final transient int RESULT_X = 74;
	public static final transient int RESULT_Y = 41;
	public static final transient int FUEL_X = 5;
	public static final transient int FUEL_Y = 77;

	public static final transient float ITEM_SCALE = 2.0F;

	@Getter
	public String title = "Smelting";
	public IngredientData input;
	public IngredientData result;
	public IngredientData fuel;
	public int cookTime = 200;
	public TextData[] description;
	public String recipe;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int x = BookScreen.PAGE_WIDTH / 2 - IMG_SMELTING.width / 2;

		int y;
		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
			y = getTitleHeight();
		}

		list.add(new ImageElement(x, y, IMG_SMELTING.width, IMG_SMELTING.height, IMG_SMELTING, book.appearance.slotColor));
		list.add(new TooltipElement(ImmutableList.of(new TranslatableComponent("mantle:tooltip.cooktime", this.cookTime / 20)), x + 7, y + 42, 60, 28));

		if (this.input != null && !this.input.getItems().isEmpty()) {
			list.add(new ItemElement(x + INPUT_X, y + INPUT_Y, ITEM_SCALE, this.input.getItems(), this.input.action));
		}

		if (this.result != null && !this.result.getItems().isEmpty()) {
			list.add(new ItemElement(x + RESULT_X, y + RESULT_Y, ITEM_SCALE, this.result.getItems(), this.result.action));
		}

		list.add(new ItemElement(x + FUEL_X, y + FUEL_Y, ITEM_SCALE, this.getFuelsList()));

		if (this.description != null && this.description.length > 0) {
			list.add(new TextElement(0, IMG_SMELTING.height + y + 5, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - y - 5, this.description));
		}
	}

	public NonNullList<ItemStack> getFuelsList() {
		if (this.fuel != null) {
			return this.fuel.getItems();
		}

		return FUELS;
	}

	@Override
	public void load() {
		super.load();

		if (!StringUtils.isEmpty(this.recipe) && ResourceLocation.isValidResourceLocation(this.recipe)) {
			Recipe<?> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(new ResourceLocation(this.recipe)).orElse(null);

			if (recipe instanceof AbstractCookingRecipe) {
				this.input = IngredientData.getItemStackData(NonNullList.of(ItemStack.EMPTY, recipe.getIngredients().get(0).getItems()));
				this.cookTime = ((AbstractCookingRecipe) recipe).getCookingTime();
				this.result = IngredientData.getItemStackData(recipe.getResultItem());
			}
		}
	}

	static {
		FUELS = NonNullList.of(ItemStack.EMPTY, new ItemStack(Blocks.OAK_SLAB), new ItemStack(Blocks.SPRUCE_SLAB), new ItemStack(Blocks.BIRCH_SLAB), new ItemStack(Blocks.JUNGLE_SLAB), new ItemStack(Blocks.ACACIA_SLAB), new ItemStack(Blocks.DARK_OAK_SLAB), new ItemStack(Blocks.OAK_PLANKS), new ItemStack(Blocks.SPRUCE_PLANKS), new ItemStack(Blocks.BIRCH_PLANKS), new ItemStack(Blocks.JUNGLE_PLANKS), new ItemStack(Blocks.ACACIA_PLANKS), new ItemStack(Blocks.DARK_OAK_PLANKS), new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Items.WOODEN_PICKAXE), new ItemStack(Items.WOODEN_SWORD), new ItemStack(Items.WOODEN_HOE), new ItemStack(Items.STICK), new ItemStack(Items.COAL), new ItemStack(Items.LAVA_BUCKET), new ItemStack(Blocks.OAK_SAPLING), new ItemStack(Blocks.SPRUCE_SAPLING), new ItemStack(Blocks.BIRCH_SAPLING), new ItemStack(Blocks.JUNGLE_SAPLING), new ItemStack(Blocks.ACACIA_SAPLING), new ItemStack(Blocks.DARK_OAK_SAPLING), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.WOODEN_SHOVEL), new ItemStack(Items.WOODEN_AXE));
	}
}