package tyrannotitanlib.library;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import tyrannotitanlib.library.base.block.TyrannoLogBlock;
import tyrannotitanlib.library.base.item.TyrannoSpawnEggItem;
import tyrannotitanlib.library.tyrannomation.network.TyrannomationNetwork;
import tyrannotitanlib.library.tyrannomation.resource.ResourceListener;

public class TyrannotitanMod 
{
	public static volatile boolean hasInitialized;

	public static String id;

	// Basic Features
	public static void initStrippingMap(String modId) 
	{
		id = modId;
		DeferredWorkQueue.runLater(() -> 
		{
			TyrannoLogBlock.addStripping();
		});
	}

	// Spawn Eggs
	public static void initSpawnEggs() 
	{
		TyrannoSpawnEggItem.initSpawnEggs();
	}

	// Tyrannomation
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
