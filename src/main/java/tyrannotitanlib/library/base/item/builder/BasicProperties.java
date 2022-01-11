package tyrannotitanlib.library.base.item.builder;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class BasicProperties extends Properties {
	public static Properties debug() {
		return FMLEnvironment.production ? new Properties().tab(CreativeModeTab.TAB_MISC) : new Properties();
	}

	public static Properties simple(CreativeModeTab itemGroup) {
		return new Properties().tab(itemGroup);
	}

	public static Properties customStackSize(CreativeModeTab itemGroup, int stackSize) {
		return simple(itemGroup).stacksTo(stackSize);
	}

	public static Properties customDurability(CreativeModeTab itemGroup, int durability) {
		return customStackSize(itemGroup, 1).defaultDurability(durability);
	}

	public static Properties customFood(CreativeModeTab itemGroup, FoodProperties food) {
		return simple(itemGroup).food(food);
	}

	public static Properties customFoodWithStackSize(CreativeModeTab itemGroup, FoodProperties food, int stackSize) {
		return customStackSize(itemGroup, stackSize).food(food);
	}
}
