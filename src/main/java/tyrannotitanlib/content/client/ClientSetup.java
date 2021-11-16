package tyrannotitanlib.content.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tyrannotitanlib.content.client.chest.TyrannoChestBlockEntityRender;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.content.server.init.TyrannoEntities;
import tyrannotitanlib.library.base.client.TyrannoBoatRenderer;
import tyrannotitanlib.library.utils.TyrannoUtils;

@EventBusSubscriber(modid = TyrannoUtils.TYRANNO_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetup 
{
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event)
	{		
		ClientRegistry.bindTileEntityRenderer(TyrannoBlockEntities.CHEST_BLOCK_ENTITY, TyrannoChestBlockEntityRender::new);
		ClientRegistry.bindTileEntityRenderer(TyrannoBlockEntities.TRAPPED_CHEST_BLOCK_ENTITY, TyrannoChestBlockEntityRender::new);

		RenderingRegistry.registerEntityRenderingHandler(TyrannoEntities.BOAT, manager -> new TyrannoBoatRenderer(manager));
	}
}
