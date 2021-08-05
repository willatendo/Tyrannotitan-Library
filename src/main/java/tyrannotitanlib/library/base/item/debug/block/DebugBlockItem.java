package tyrannotitanlib.library.base.item.debug.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.loading.FMLEnvironment;
import tyrannotitanlib.library.base.item.builder.Builder;

public class DebugBlockItem extends BlockItem
{
	protected DebugBlockItem(Block block, Properties properties) 
	{
		super(block, properties);
	}
	
	public static Item create(Block block)
	{
		return !FMLEnvironment.production ? new DebugBlockItem(block, Builder.debug().tab(ItemGroup.TAB_MISC)) : new DebugBlockItem(block, Builder.debug());
	}
}
