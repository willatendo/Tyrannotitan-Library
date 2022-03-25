package tyrannotitanlib;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import tyrannotitanlib.tyrannibook.client.TyrannobookLoader;
import tyrannotitanlib.tyranninetwork.Tyrannonetwork;

public class TyrannibookHelper {
	// Use This
	public void commonSetup(FMLCommonSetupEvent event) {
		Tyrannonetwork.registerPackets();
	}

	// Use This
	public static void listenersSetup(FMLClientSetupEvent event) {
		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if(manager instanceof IReloadableResourceManager) 
		{
			((IReloadableResourceManager)manager).registerReloadListener(new TyrannobookLoader());
		}
	}
}
