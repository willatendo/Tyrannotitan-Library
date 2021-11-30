package tyrannotitanlib.library.base.biome;

import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;

public class TyrannoBiome 
{
	private final Biome biome;
	
	public TyrannoBiome(Biome.BiomeBuilder builder) 
	{
		this.biome = builder.build();
	}
	
	public Biome getBiome()
	{
		return this.biome;
	}
	
	public static int calculateSkyColor(float temperature) 
	{
		float colour = temperature / 3.0F;
		colour = Mth.clamp(colour, -1.0F, 1.0F);
		return Mth.hsvToRgb(0.62222224F - colour * 0.05F, 0.5F + colour * 0.1F, 1.0F);
	}
	
	public static MobSpawnSettings.Builder defaultOverworldSpawns() 
	{
		MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.farmAnimals(mobspawninfo$builder);
		BiomeDefaultFeatures.commonSpawns(mobspawninfo$builder);
		return mobspawninfo$builder;
	}
	
	public static <C extends SurfaceBuilderConfiguration> BiomeGenerationSettings.Builder genSettings(SurfaceBuilder<C> surfaceBuilder, C config) 
	{
		return new BiomeGenerationSettings.Builder().surfaceBuilder(surfaceBuilder.configured(config));
	}
}
