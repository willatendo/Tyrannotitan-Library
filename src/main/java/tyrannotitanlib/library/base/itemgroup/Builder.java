package tyrannotitanlib.library.base.itemgroup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import tyrannotitanlib.library.TyrannotitanMod;

public class Builder extends ItemGroup
{
	public ItemStack icon;
	
	public Builder(String id, ItemStack icon) 
	{	
		super(TyrannotitanMod.id + "." + id);

		this.icon = icon;
	}

	@Override
	public ItemStack makeIcon() 
	{
		return icon;
	}
}
