package tyrannotitanlib.library.base.item.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;

import net.minecraft.world.item.Item.Properties;

public class TyrannoSignItem extends SignItem
{	
	public TyrannoSignItem(Properties properties, Block standingBlock, Block wallBlock) 
	{
		super(properties, standingBlock, wallBlock);
	}
	
	@Override
	public int getBurnTime(ItemStack stack) 
	{
		return 200;
	}
}
