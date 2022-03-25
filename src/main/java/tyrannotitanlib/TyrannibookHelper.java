package tyrannotitanlib;

import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import tyrannotitanlib.tyrannibook.TyrannobookLoader;
import tyrannotitanlib.tyranninetwork.Tyrannonetwork;

public class TyrannibookHelper {
	// Use This
	public void commonSetup(FMLCommonSetupEvent event) {
		Tyrannonetwork.registerPackets();
	}

	// Use This
	public static void listenersSetup(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(new TyrannobookLoader());
	}
}
