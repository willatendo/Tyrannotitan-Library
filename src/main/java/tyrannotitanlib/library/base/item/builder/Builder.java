package tyrannotitanlib.library.base.item.builder;

import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;

public class Builder 
{	
	public static Properties debug()
	{
		return new Properties();
	}
	
	public static Properties simple(ItemGroup group)
	{
		return new Properties().tab(group);
	}
}
