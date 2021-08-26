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
	public static void initBasicFeatures(String modId) 
	{
		id = modId;
		DeferredWorkQueue.runLater(() -> 
		{
			TyrannoLogBlock.addStripping();
		});
	}

	// Spawn Eggs
	public static void initSpawnEggs(String modId) 
	{
		TyrannoSpawnEggItem.initSpawnEggs();
	}

	// Tyrannomation
	public static void initTyrannomation(String modId) 
	{
		if(!hasInitialized) 
		{
			DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ResourceListener::registerReloadListener);
			TyrannomationNetwork.initialize();
		}
		hasInitialized = true;
	}

	// Tyrannotextures
//	public void initTyrannotextures(String modId) 
//	{
//		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> 
//		{
//			IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
//			modBus.addListener(this::modelRegistry);
//			modBus.register(TextureMetadataHandler.INSTANCE);
//			modBus.register(new CTMPackReloadListener());
//
//			TextureTypeRegistry.scan();
//		});
//	}

//	private void modelRegistry(ModelRegistryEvent event) 
//	{
//		ModelLoaderRegistry.registerLoader(TyrannoUtils.rL("tyrannotextures"), ModelLoaderCTM.INSTANCE);
//	}
}
