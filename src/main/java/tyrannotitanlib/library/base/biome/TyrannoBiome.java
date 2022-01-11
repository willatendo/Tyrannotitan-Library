package tyrannotitanlib.library.base.biome;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tyrannotitanlib.library.utils.TyrannoUtils;

@EventBusSubscriber(bus = Bus.MOD, modid = TyrannoUtils.TYRANNO_ID)
public abstract class TyrannoBiome {
	@Getter
	public SurfaceRules.RuleSource topBlock;
	@Getter
	public SurfaceRules.RuleSource midBlock;
	@Setter
	public Block topLayer = Blocks.GRASS_BLOCK;
	@Setter
	public Block midLayer = Blocks.DIRT;
	@Getter
	public final Biome biome;
	@Getter
	public final ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, this.name());

	public TyrannoBiome(Biome.BiomeBuilder builder) {
		this.biome = builder.build();
		BiomeGeneration.BIOMES.add(this);

		topBlock = makeStateRule(this.topLayer);
		midBlock = makeStateRule(this.midLayer);
	}

	public SurfaceRules.RuleSource makeStateRule(Block block) {
		return SurfaceRules.state(block.defaultBlockState());
	}
	
	public abstract ResourceLocation name();

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

	public static final class BaseBiomeInfo {
		//Water Colour
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

		//Fog Colour
		public static final int BASE_FOG_COLOUR = 12638463;
	}
}
