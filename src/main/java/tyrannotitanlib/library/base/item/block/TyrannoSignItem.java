package tyrannotitanlib.library.base.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.util.text.ITextComponent;
import tyrannotitanlib.library.base.util.TyrannoUtils;

public class TyrannoSignItem extends SignItem
{
	private String itemName;
	
	public TyrannoSignItem(final String name, final Properties properties, final ItemGroup group, final Block standingBlock, final Block wallBlock) 
	{
		super(properties.tab(group), standingBlock, wallBlock);
		this.itemName = name;
	}
	
	@Override
	public ITextComponent getName(ItemStack stack) 
	{
		return TyrannoUtils.sTC(itemName);
	}
	
	@Override
	public int getBurnTime(ItemStack stack) 
	{
		return 200;
	}
}
