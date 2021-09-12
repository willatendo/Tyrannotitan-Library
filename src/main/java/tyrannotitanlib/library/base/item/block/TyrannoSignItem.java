package tyrannotitanlib.library.base.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;

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
