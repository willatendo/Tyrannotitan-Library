package tyrannotitanlib.library.base.biome;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;

public class BaseBiomeInfo {
	// Water Colour
	public static final int BASE_WATER_COLOUR = 4159204;
	public static final int BASE_WATER_FOG_COLOUR = 329011;

	public static final int OCEAN_WATER_COLOUR = 4159204;
	public static final int OCEAN_WATER_FOG_COLOUR = 4159204;

	public static final int LUKE_WARM_OCEAN_WATER_COLOUR = 4566514;
	public static final int LUKE_WARM_OCEAN_WATER_FOG_COLOUR = 267827;

	public static final int WARM_OCEAN_WATER_COLOUR = 4445678;
	public static final int WARM_OCEAN_WATER_FOG_COLOUR = 270131;

	public static final int COLD_OCEAN_WATER_COLOUR = 4020182;
	public static final int COLD_OCEAN_WATER_FOG_COLOUR = 329011;

	// Fog Colour
	public static final int BASE_FOG_COLOUR = 12638463;

	public static Biome.BiomeBuilder biome(Precipitation rain, BiomeCategory category, float depth, float scale, float downfall, float temperature, BiomeSpecialEffects ambience, BiomeGenerationSettings settings, MobSpawnSettings spawningInfo) {
		return new Biome.BiomeBuilder().precipitation(rain).biomeCategory(category).depth(depth).scale(scale).downfall(downfall).temperature(temperature).generationSettings(settings).specialEffects(ambience).mobSpawnSettings(spawningInfo);
	}
}
