package tyrannotitanlib.library.base.itemgroup;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tyrannotitanlib.library.TyrannotitanMod;

public class Builder extends ItemGroup
{
	public ItemStack itemIcon;
	
	public Builder(String tabId, ItemStack itemIcon) 
	{	
		super(TyrannotitanMod.id + "." + tabId);

		this.itemIcon = itemIcon;
	}
	
	public Builder(String tabId, Block blockIcon)
	{
		this(tabId, blockIcon.asItem().getDefaultInstance());
	}
	
	@Override
	public ItemStack makeIcon() 
	{
		return itemIcon;
	}
}
