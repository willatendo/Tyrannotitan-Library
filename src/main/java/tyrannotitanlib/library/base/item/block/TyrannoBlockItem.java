package tyrannotitanlib.library.base.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import tyrannotitanlib.library.base.util.TyrannoUtils;

/*
 * This is a BlockItem class that gives a BlockItem a ItemGroup and name without too much trouble.
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

public class TyrannoBlockItem extends BlockItem
{
	private String itemName;
	
	public TyrannoBlockItem(final String name, final Properties properties, final ItemGroup group, final Block block) 
	{
		super(block, properties.tab(group));
		this.itemName = name;
	}
	
	@Override
	public ITextComponent getName(ItemStack stack) 
	{
		return TyrannoUtils.sTC(itemName);
	}
}
