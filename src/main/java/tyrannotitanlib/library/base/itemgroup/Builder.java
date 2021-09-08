package tyrannotitanlib.library.base.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tyrannotitanlib.library.TyrannotitanMod;

public class Builder extends ItemGroup
{
	public ItemStack itemIcon;
	
	public Builder(String tabId) 
	{	
		super(TyrannotitanMod.id + "." + tabId);
	}
	
	@Override
	public ItemStack makeIcon() 
	{
		return itemIcon;
	}
	
	public void setIcon(ItemStack icon)
	{
		this.itemIcon = icon;
	}
}
