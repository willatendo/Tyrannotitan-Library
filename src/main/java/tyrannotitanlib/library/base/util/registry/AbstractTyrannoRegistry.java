package tyrannotitanlib.library.base.util.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class AbstractTyrannoRegistry<T extends IForgeRegistryEntry<T>> implements ITyrannoRegistry<T>
{
	protected final TyrannoRegistry registry;
	protected final DeferredRegister<T> deferredRegister;
	
	public AbstractTyrannoRegistry(TyrannoRegistry registry, DeferredRegister<T> deferredRegister) 
	{
		this.registry = registry;
		this.deferredRegister = deferredRegister;
	}
	
	@Override
	public TyrannoRegistry getRegistry() 
	{
		return this.registry;
	}
	
	@Override
	public DeferredRegister<T> getDeferredRegister() 
	{
		return this.deferredRegister;
	}
	
	@Override
	public void register(IEventBus bus) 
	{
		this.getDeferredRegister().register(bus);
	}
	
	public static boolean areModsLoaded(String... modIds)
	{
		ModList modList = ModList.get();
		for(String mod : modIds)
		{
			if(!modList.isLoaded(mod))
			{
				return false;
			}
		}
		return true;
	}
}
