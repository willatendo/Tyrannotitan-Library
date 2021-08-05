package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoWorldTypeRegister extends AbstractTyrannoRegistry<ForgeWorldType>
{
	
	public TyrannoWorldTypeRegister(TyrannoRegistry registry, DeferredRegister<ForgeWorldType> deferredRegister) 
	{
		super(registry, deferredRegister);
	}

	public TyrannoWorldTypeRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.WORLD_TYPES, registry.getModId()));
	}
	
	public ForgeWorldType build(String id, ForgeWorldType worldType)
	{
		this.deferredRegister.register(id, () -> worldType);
		return worldType;
	}
}
