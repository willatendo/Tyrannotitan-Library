package tyrannotitanlib.library.base.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class TyrannoBurningBlockItem extends BlockItem
{
	private int burnTime;

	public TyrannoBurningBlockItem(Properties properties, Block block, int burnTime) 
	{
		super(block, properties);
		this.burnTime = burnTime;
	}

	@Override
	public int getBurnTime(ItemStack stack) 
	{
		return this.burnTime;
	}
}
