package tyrannotitanlib.library.base.biome;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class TyrannoBiomeAdder 
{
	public static void addBiome(BiomeLoadingEvent event, ResourceLocation loc, BiomeType temp, RegistryKey<Biome> key, int weight, Type... types)
	{
		if(event.getName().equals(loc))
		{
			BiomeManager.addBiome(temp, new BiomeManager.BiomeEntry(key, weight));
			BiomeDictionary.addTypes(key, types);
		}
	}
}
