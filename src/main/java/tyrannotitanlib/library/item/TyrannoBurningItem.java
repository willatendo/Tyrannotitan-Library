package tyrannotitanlib.library.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class TyrannoBurningItem extends Item {
	private int burnTime;

	public TyrannoBurningItem(Properties properties, int burnTime) {
		super(properties);
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return this.burnTime;
	}
}
