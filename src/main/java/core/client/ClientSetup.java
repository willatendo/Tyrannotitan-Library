package core.client;

import static core.content.Util.TYRANNO_ID;

import core.client.chest.TyrannoChestBlockEntityRender;
import core.content.init.TyrannoBlockEntities;
import core.content.init.TyrannoEntities;
import core.library.tyrannotitanlib.client.TyrannoBoatRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@EventBusSubscriber(modid = TYRANNO_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(ClientSetup::rendererSetup);
	}

	public static void rendererSetup(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(TyrannoBlockEntities.CHEST_BLOCK_ENTITY, TyrannoChestBlockEntityRender::new);
		event.registerBlockEntityRenderer(TyrannoBlockEntities.CHEST_BLOCK_ENTITY, TyrannoChestBlockEntityRender::new);

		event.registerEntityRenderer(TyrannoEntities.BOAT, manager -> new TyrannoBoatRenderer(manager));
	}
}
