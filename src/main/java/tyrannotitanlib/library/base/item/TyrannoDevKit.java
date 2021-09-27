package tyrannotitanlib.library.base.item;

import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.loading.FMLEnvironment;
import tyrannotitanlib.content.server.util.TyrannoRegistries;

public class TyrannoDevKit extends Item
{
	private static final TyrannoItemTeir DEV_TEIR = new TyrannoItemTeir(4, 10000000, 1000.0F, 1000.0F, 1000, () -> {
		return Ingredient.of(Items.DRAGON_EGG);
	});
	
	private static Item dev_tool_sword = new SwordItem(DEV_TEIR, 0, 0, new Properties().tab(ItemGroup.TAB_MISC));
	private static Item dev_tool_shovel = new ShovelItem(DEV_TEIR, 0, 0, new Properties().tab(ItemGroup.TAB_MISC));
	private static Item dev_tool_pickaxe = new PickaxeItem(DEV_TEIR, 0, 0, new Properties().tab(ItemGroup.TAB_MISC));
	private static Item dev_tool_axe = new AxeItem(DEV_TEIR, 0, 0, new Properties().tab(ItemGroup.TAB_MISC));
	private static Item dev_tool_hoe = new HoeItem(DEV_TEIR, 0, 0, new Properties().tab(ItemGroup.TAB_MISC));
	
	public TyrannoDevKit(Properties properties) 
	{
		super(properties);
	}
	
	public static void init()
	{
		if(!FMLEnvironment.production)
		{
			TyrannoRegistries.create("dev.tool.sword", dev_tool_sword);
			TyrannoRegistries.create("dev.tool.shovel", dev_tool_shovel);
			TyrannoRegistries.create("dev.tool.pickaxe", dev_tool_pickaxe);
			TyrannoRegistries.create("dev.tool.axe", dev_tool_axe);
			TyrannoRegistries.create("dev.tool.hoe", dev_tool_hoe);
		}
	}
}
