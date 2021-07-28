package tyrannotitanlib.library.base.item;

import net.minecraft.item.Food;
import net.minecraft.item.Item;

/*
 * This is a Item class that gives a Item a ItemGroup, Food, and a name.
 * To change the name in another language, use the name that you set.
 * 
 * In your item class, make a register that fills in the ItemGroup.
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

public class TyrannoFoodItem extends Item
{
	public TyrannoFoodItem(final Properties properties, final Food food) 
	{
		super(properties.food(food));
	}
}
