package tyrannotitanlib.library.base.util.interfaces;

import javax.annotation.Nullable;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;

@FunctionalInterface
public interface ITyrannoEdgeBiomeProvider 
{
	@Nullable
	RegistryKey<Biome> getEdgeBiome(INoiseRandom rand, RegistryKey<Biome> northBiome, RegistryKey<Biome> westBiome, RegistryKey<Biome> southBiome, RegistryKey<Biome> eastBiome);
}