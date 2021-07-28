package tyrannotitanlib.library.base.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/*
 * This is a BlockItem class that gives a BlockItem a burn time, ItemGroup, and name without too much trouble.
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

public class TyrannoBurningBlockItem extends TyrannoBlockItem
{
	private int burnTime;

	public TyrannoBurningBlockItem(final String name, final Properties properties, final ItemGroup group, final Block block, final int burnTime) 
	{
		super(name, properties, group, block);
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTime(ItemStack stack) 
	{
		return this.burnTime;
	}
}
