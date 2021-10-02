package tyrannotitanlib.library;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import tyrannotitanlib.content.server.util.TyrannoRegistries;
import tyrannotitanlib.library.base.block.TyrannoLogBlock;
import tyrannotitanlib.library.base.item.TyrannoSpawnEggItem;
import tyrannotitanlib.library.tyrannomation.network.TyrannomationNetwork;
import tyrannotitanlib.library.tyrannomation.resource.ResourceListener;

public class TyrannotitanMod 
{
	public static volatile boolean hasInitialized;
	public static String id;

	// Mandatory!
	public static void init(String modid)
	{
		id = modid;
		TyrannoRegistries.register();
		TyrannoSpawnEggItem.initSpawnEggs();
		DeferredWorkQueue.runLater(() -> 
		{
			TyrannoLogBlock.addStripping();
		});
		initTyrannomation();
	}

	private static void initTyrannomation() 
	{
		if(!hasInitialized) 
		{
			DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ResourceListener::registerReloadListener);
			TyrannomationNetwork.initialize();
		}
		hasInitialized = true;
	}
}
