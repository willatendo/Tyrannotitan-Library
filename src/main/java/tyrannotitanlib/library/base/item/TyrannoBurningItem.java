package tyrannotitanlib.library.base.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TyrannoBurningItem extends Item
{
	private int burnTime;

	public TyrannoBurningItem(Properties properties, int burnTime) 
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
