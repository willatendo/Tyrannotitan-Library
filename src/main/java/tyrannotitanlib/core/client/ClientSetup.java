package tyrannotitanlib.core.client;

import static tyrannotitanlib.core.content.Util.TYRANNO_ID;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tyrannotitanlib.core.client.chest.TyrannoChestBlockEntityRender;
import tyrannotitanlib.core.content.init.TyrannoBlockEntities;
import tyrannotitanlib.core.content.init.TyrannoEntities;
import tyrannotitanlib.library.client.TyrannoBoatRenderer;

@EventBusSubscriber(modid = TYRANNO_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(ClientSetup::rendererSetup);
	}

	public static void rendererSetup(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(TyrannoBlockEntities.TYRANNO_CHEST.get(), TyrannoChestBlockEntityRender::new);
		event.registerBlockEntityRenderer(TyrannoBlockEntities.TYRANNO_CHEST.get(), TyrannoChestBlockEntityRender::new);

		event.registerEntityRenderer(TyrannoEntities.TYRANNO_BOAT.get(), manager -> new TyrannoBoatRenderer(manager));
	}
}
