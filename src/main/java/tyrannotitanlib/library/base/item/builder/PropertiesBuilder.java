package tyrannotitanlib.library.base.item.builder;

import net.minecraft.item.Food;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class PropertiesBuilder extends Properties
{	
	public static Properties debug()
	{
		return FMLEnvironment.production ? new Properties().tab(ItemGroup.TAB_MISC) : new Properties();
	}
	
	public static Properties simple(ItemGroup itemGroup)
	{
		return new Properties().tab(itemGroup);
	}
	
	public static Properties customStackSize(ItemGroup itemGroup, int stackSize)
	{
		return simple(itemGroup).stacksTo(stackSize);
	}
	
	public static Properties customDurability(ItemGroup itemGroup, int durability)
	{
		return customStackSize(itemGroup, 1).defaultDurability(durability);
	}
	
	public static Properties customFood(ItemGroup itemGroup, Food food)
	{
		return simple(itemGroup).food(food);
	}
	
	public static Properties customFoodWithStackSize(ItemGroup itemGroup, Food food, int stackSize)
	{
		return customStackSize(itemGroup, stackSize).food(food);
	}
}
