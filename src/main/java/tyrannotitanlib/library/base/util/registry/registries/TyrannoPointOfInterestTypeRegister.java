package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoPointOfInterestTypeRegister extends AbstractTyrannoRegistry<PointOfInterestType>
{
	public TyrannoPointOfInterestTypeRegister(TyrannoRegistry registry, DeferredRegister<PointOfInterestType> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoPointOfInterestTypeRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.POI_TYPES, registry.getModId()));
	}
	
	public PointOfInterestType build(String id, PointOfInterestType pointOfInterestType)
	{
		this.deferredRegister.register(id, () -> pointOfInterestType);
		return pointOfInterestType;
	}
}
