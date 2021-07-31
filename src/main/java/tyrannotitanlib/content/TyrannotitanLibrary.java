package tyrannotitanlib.content;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tyrannotitanlib.library.base.util.TyrannoUtils;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

@Mod(TyrannoUtils.TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public static final TyrannoRegistry REGISTRY = new TyrannoRegistry(TyrannoUtils.TYRANNO_ID);

	public TyrannotitanLibrary() 
	{
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		REGISTRY.register(bus);
	}
}
