package tyrannotitanlib.library.base.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TabBuilder extends ItemGroup
{
	public ItemStack itemIcon;
	
	public TabBuilder(String modId, String tabId) 
	{	
		super(modId + "." + tabId);
	}
	
	@Override
	public ItemStack makeIcon() 
	{
		return itemIcon;
	}
	
	//Set this in a Common event
	public void setIcon(ItemStack icon)
	{
		this.itemIcon = icon;
	}
}
