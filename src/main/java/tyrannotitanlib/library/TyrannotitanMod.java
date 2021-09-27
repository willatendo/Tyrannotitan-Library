package tyrannotitanlib.library;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import tyrannotitanlib.content.server.util.TyrannoRegistries;
import tyrannotitanlib.library.base.block.TyrannoLogBlock;
import tyrannotitanlib.library.base.item.TyrannoSpawnEggItem;
import tyrannotitanlib.library.tyrannobook.item.ModTyrannobookItem;
import tyrannotitanlib.library.tyrannomation.network.TyrannomationNetwork;
import tyrannotitanlib.library.tyrannomation.resource.ResourceListener;

public class TyrannotitanMod 
{
	public static volatile boolean hasInitialized;
	public static String id;

	// Mandatory!
	public static void initBase(String modid)
	{
		id = modid;
		TyrannoRegistries.register();
		TyrannoSpawnEggItem.initSpawnEggs();
		DeferredWorkQueue.runLater(() -> 
		{
			TyrannoLogBlock.addStripping();
		});
	}
	
	// Mandatory for Tyrannobook
	public static void createModBook(String name, ItemGroup group)
	{
		TyrannoRegistries.create(id, name, new ModTyrannobookItem(new Properties().tab(group).stacksTo(1)));
	}
	
	// Mandatory for Tyrannoregistry
	public static void initTyrannoregistry()
	{
		
	}

	// Mandatory for Tyrannomataion
	public static void initTyrannomation() 
	{
		if(!hasInitialized) 
		{
			DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ResourceListener::registerReloadListener);
			TyrannomationNetwork.initialize();
		}
		hasInitialized = true;
	}
}
