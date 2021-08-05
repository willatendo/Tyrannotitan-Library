package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoBiomeRegister extends AbstractTyrannoRegistry<Biome>
{
	
	public TyrannoBiomeRegister(TyrannoRegistry registry, DeferredRegister<Biome> deferredRegister) 
	{
		super(registry, deferredRegister);
	}

	public TyrannoBiomeRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.BIOMES, registry.getModId()));
	}
	
	public Biome build(String id, Biome biome)
	{
		this.deferredRegister.register(id, () -> biome);
		return biome;
	}
	
	public RegistryKey<Biome> key(String modId, String id)
	{
		return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(modId, id));
	}
}
