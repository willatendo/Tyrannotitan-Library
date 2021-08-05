package tyrannotitanlib.library.base.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class TyrannoFoodItem extends Item
{
	public TyrannoFoodItem(Properties properties, Food food) 
	{
		super(properties.food(food));
	}
}
