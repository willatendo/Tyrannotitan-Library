package tyrannotitanlib.content.client;

import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;
import tyrannotitanlib.library.base.util.TyrannoUtils;

@EventBusSubscriber(modid = TyrannoUtils.TYRANNO_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetup 
{
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event)
	{
		ClientRegistry.bindTileEntityRenderer(TyrannoTileEntities.SIGN_TILE_ENTITY, SignTileEntityRenderer::new);
	}
}
