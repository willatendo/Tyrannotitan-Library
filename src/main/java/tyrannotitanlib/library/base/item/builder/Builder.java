package tyrannotitanlib.library.base.item.builder;

import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;

public class Builder extends Properties
{	
	public static Properties debug()
	{
		return new Properties();
	}
	
	public static Properties simple(ItemGroup itemGroup)
	{
		return new Properties().tab(itemGroup);
	}
	
	public static Properties customStackSize(ItemGroup itemGroup, int stackSize)
	{
		return simple(itemGroup).stacksTo(stackSize);
	}
}
