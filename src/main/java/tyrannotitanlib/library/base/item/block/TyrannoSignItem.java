package tyrannotitanlib.library.base.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.util.text.ITextComponent;
import tyrannotitanlib.library.base.util.TyrannoUtils;

/*
 * This is a SignItem class that gives a SignItem a burn time, ItemGroup, and name without too much trouble.
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
