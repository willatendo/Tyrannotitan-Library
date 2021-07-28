package tyrannotitanlib.library.base.util.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ITyrannoRegistry<T extends IForgeRegistryEntry<T>>
{
	TyrannoRegistry getRegistry();
	
	DeferredRegister<T> getDeferredRegister();
	
	void register(final IEventBus bus);
}
