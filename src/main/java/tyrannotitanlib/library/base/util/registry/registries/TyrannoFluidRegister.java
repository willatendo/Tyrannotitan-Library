package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoFluidRegister extends AbstractTyrannoRegistry<Fluid>
{
	public TyrannoFluidRegister(TyrannoRegistry registry, DeferredRegister<Fluid> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoFluidRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.FLUIDS, registry.getModId()));
	}
	
	public Fluid build(String id, Fluid fluid)
	{
		this.deferredRegister.register(id, () -> fluid);
		return fluid;
	}
}
