package tyrannotitanlib.library.base.item.debug;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.loading.FMLEnvironment;
import tyrannotitanlib.library.base.item.builder.Builder;

public class DebugItem extends Item
{
	protected DebugItem(Properties properties) 
	{
		super(properties);
	}
	
	public static Item create()
	{
		return !FMLEnvironment.production ? new DebugItem(Builder.debug().tab(ItemGroup.TAB_MISC)) : new DebugItem(Builder.debug());
	}
}
