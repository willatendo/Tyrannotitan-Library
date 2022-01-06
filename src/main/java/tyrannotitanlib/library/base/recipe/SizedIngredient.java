package tyrannotitanlib.library.base.recipe;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import tyrannotitanlib.library.tyrannobook.JsonHelper;

@RequiredArgsConstructor(staticName = "of")
public class SizedIngredient implements Predicate<ItemStack> {
	public static final SizedIngredient EMPTY = of(Ingredient.EMPTY, 0);

	private final Ingredient ingredient;
	@Getter
	private final int amountNeeded;

	private WeakReference<ItemStack[]> lastIngredientMatch;
	private List<ItemStack> matchingStacks;

	public static SizedIngredient of(Ingredient ingredient) {
		return of(ingredient, 1);
	}

	public static SizedIngredient fromItems(int amountNeeded, ItemLike... items) {
		return of(Ingredient.of(items), amountNeeded);
	}

	public static SizedIngredient fromItems(ItemLike... items) {
		return fromItems(1, items);
	}

	public static SizedIngredient fromTag(Tag<Item> tag, int amountNeeded) {
		return of(Ingredient.of(tag), amountNeeded);
	}

	public static SizedIngredient fromTag(Tag<Item> tag) {
		return fromTag(tag, 1);
	}

	@Override
	public boolean test(ItemStack stack) {
		return stack.getCount() >= this.amountNeeded && this.ingredient.test(stack);
	}

	public boolean hasNoMatchingStacks() {
		return this.ingredient.isEmpty();
	}
	
	public List<ItemStack> getMatchingStacks() {
		ItemStack[] ingredientMatch = this.ingredient.getItems();
		if (this.matchingStacks == null || this.lastIngredientMatch.get() != ingredientMatch) {
			this.matchingStacks = Arrays.stream(ingredientMatch).map(stack -> {
				if (stack.getCount() != this.amountNeeded) {
					stack = stack.copy();
					stack.setCount(this.amountNeeded);
				}
				return stack;
			}).collect(Collectors.toList());
			this.lastIngredientMatch = new WeakReference<>(ingredientMatch);
		}
		return this.matchingStacks;
	}

	public void write(FriendlyByteBuf buffer) {
		buffer.writeVarInt(this.amountNeeded);
		this.ingredient.toNetwork(buffer);
	}

	public JsonObject serialize() {
		JsonElement ingredient = this.ingredient.toJson();
		JsonObject json = null;
		if (ingredient.isJsonObject()) {
			json = ingredient.getAsJsonObject();
			if (json.has("ingredient") || json.has("amount_needed")) {
				json = null;
			}
		}
		if (json == null) {
			json = new JsonObject();
			json.add("ingredient", ingredient);
		}
		if (this.amountNeeded != 1) {
			json.addProperty("amount_needed", this.amountNeeded);
		}
		return json;
	}
	
	public static SizedIngredient read(FriendlyByteBuf buffer) {
		int amountNeeded = buffer.readVarInt();
		Ingredient ingredient = Ingredient.fromNetwork(buffer);
		return of(ingredient, amountNeeded);
	}

	public static SizedIngredient deserialize(JsonObject json) {
		int amountNeeded = GsonHelper.getAsInt(json, "amount_needed", 1);
		Ingredient ingredient;
		if (json.has("ingredient")) {
			ingredient = Ingredient.fromJson(JsonHelper.getElement(json, "ingredient"));
		} else {
			ingredient = Ingredient.fromJson(json);
		}

		return of(ingredient, amountNeeded);
	}
}
