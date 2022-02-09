package tyrannotitanlib.tyranniworld;

import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public abstract class TyrannoBiome {
	@Getter
	public final Biome biome;
	@Getter
	public final ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, name());

	public TyrannoBiome(BiomeBuilder builder) {
		this.biome = builder.build();
	}

	public abstract ResourceLocation name();

	public static final class BaseBiomeInfo {
		// Water Colour
		public static final int BASE_WATER_COLOUR = 0x3f76e4;
		public static final int BASE_WATER_FOG_COLOUR = 0x50533;

		public static final int LUKE_WARM_OCEAN_WATER_COLOUR = 0x45adf2;
		public static final int LUKE_WARM_OCEAN_WATER_FOG_COLOUR = 0x41633;

		public static final int WARM_OCEAN_WATER_COLOUR = 0x43d5ee;
		public static final int WARM_OCEAN_WATER_FOG_COLOUR = 0x41f33;

		public static final int COLD_OCEAN_WATER_COLOUR = 0x3d57d6;
		public static final int COLD_OCEAN_WATER_FOG_COLOUR = 0x50533;

		// Fog Colour
		public static final int BASE_FOG_COLOUR = 0xc0d8ff;

		public static int calculateSkyColor(float temperature) {
			float colour = temperature / 3.0F;
			colour = Mth.clamp(colour, -1.0F, 1.0F);
			return Mth.hsvToRgb(0.62222224F - colour * 0.05F, 0.5F + colour * 0.1F, 1.0F);
		}

		public static MobSpawnSettings.Builder defaultOverworldSpawns() {
			MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
			BiomeDefaultFeatures.farmAnimals(mobspawninfo$builder);
			BiomeDefaultFeatures.commonSpawns(mobspawninfo$builder);
			return mobspawninfo$builder;
		}

		public static Biome.BiomeBuilder builder(Precipitation rain, BiomeCategory category, float downfall, float temperature, BiomeGenerationSettings settings, BiomeSpecialEffects ambience, MobSpawnSettings spawningInfo) {
			return new Biome.BiomeBuilder().precipitation(rain).biomeCategory(category).downfall(downfall).temperature(temperature).generationSettings(settings).specialEffects(ambience).mobSpawnSettings(spawningInfo);
		}
	}
}
