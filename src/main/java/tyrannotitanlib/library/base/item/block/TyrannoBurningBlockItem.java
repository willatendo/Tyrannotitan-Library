package tyrannotitanlib.library.base.item.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

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
