package tyrannotitanlib.content;

import static tyrannotitanlib.library.base.utils.TyrannoUtils.TYRANNO_ID;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tyrannotitanlib.library.TyrannotitanMod;
import tyrannotitanlib.library.network.Tyrannonetwork;

@Mod(TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public TyrannotitanLibrary() 
	{
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::commonSetup);
		
		TyrannotitanMod.init(TYRANNO_ID);
	}
	
	private void commonSetup(final FMLCommonSetupEvent event) 
	{
		Tyrannonetwork.registerPackets();
	}
}
