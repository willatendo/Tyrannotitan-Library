package tyrannotitanlib.content;

import static tyrannotitanlib.library.base.utils.TyrannoUtils.TYRANNO_ID;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tyrannotitanlib.content.server.util.TyrannoRegistries;
import tyrannotitanlib.library.network.Tyrannonetwork;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookLoader;
import tyrannotitanlib.library.tyrannobook.item.TyrannobookItem;

@Mod(TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public TyrannotitanLibrary() 
	{
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::commonSetup);
		
		TyrannoRegistries.register();
		
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, PlayerInteractEvent.RightClickBlock.class, TyrannobookItem::interactWithBlock);
	}
	
	private void commonSetup(final FMLCommonSetupEvent event) 
	{
		Tyrannonetwork.registerPackets();
		
		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if(manager instanceof IReloadableResourceManager) 
		{
			((IReloadableResourceManager)manager).registerReloadListener(new TyrannobookLoader());
		}
	}
}
