package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoParticleTypeRegister extends AbstractTyrannoRegistry<ParticleType<?>>
{
	public TyrannoParticleTypeRegister(TyrannoRegistry registry, DeferredRegister<ParticleType<?>> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoParticleTypeRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, registry.getModId()));
	}
	
	public ParticleType<?> build(String id, ParticleType<?> particle)
	{
		this.deferredRegister.register(id, () -> particle);
		return particle;
	}
}
