package tyrannotitanlib.library.base.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/*
 * This is a Item class that gives a Item a ItemGroup and name without too much trouble.
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

public class TyrannoBurningItem extends Item
{
	private int burnTime;

	public TyrannoBurningItem(final Properties properties, final int burnTime) 
	{
		super(properties);
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTime(ItemStack stack) 
	{
		return this.burnTime;
	}
}
