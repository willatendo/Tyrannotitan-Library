package tyrannotitanlib.library.base.biome;

//Until I can figure out surface rules
@Deprecated
//@EventBusSubscriber(bus = Bus.MOD, modid = TyrannoUtils.TYRANNO_ID)
public class TyrannoBiome { }
//	public static SurfaceRules.RuleSource topBlock;
//	public static SurfaceRules.RuleSource midBlock;
//	private Block topLayer = Blocks.GRASS_BLOCK;
//	private Block midLayer = Blocks.DIRT;
//	private final Biome biome;
//
//	public TyrannoBiome(Biome.BiomeBuilder builder) {
//		this.biome = builder.build();
//
//		topBlock = makeStateRule(this.topLayer);
//		midBlock = makeStateRule(this.midLayer);
//
//		SurfaceRules.ConditionSource surfacerules$conditionsource6 = SurfaceRules.waterBlockCheck(-1, 0);
//		SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource6, midBlock), topBlock);
//	}
//
//	public static SurfaceRules.RuleSource makeStateRule(Block block) {
//		return SurfaceRules.state(block.defaultBlockState());
//	}
//
//	public void setTopLayer(Block topLayer) {
//		this.topLayer = topLayer;
//	}
//
//	public void setMidLayer(Block midLayer) {
//		this.midLayer = midLayer;
//	}
//
//	public Biome getBiome() {
//		return this.biome;
//	}
//
//	public static int calculateSkyColor(float temperature) {
//		float colour = temperature / 3.0F;
//		colour = Mth.clamp(colour, -1.0F, 1.0F);
//		return Mth.hsvToRgb(0.62222224F - colour * 0.05F, 0.5F + colour * 0.1F, 1.0F);
//	}
//
//	public static MobSpawnSettings.Builder defaultOverworldSpawns() {
//		MobSpawnSettings.Builder mobspawninfo$builder = new MobSpawnSettings.Builder();
//		BiomeDefaultFeatures.farmAnimals(mobspawninfo$builder);
//		BiomeDefaultFeatures.commonSpawns(mobspawninfo$builder);
//		return mobspawninfo$builder;
//	}
//
//	public static final class BaseBiomeInfo {
//		// Water Colour
//		public static final int BASE_WATER_COLOUR = 4159204;
//		public static final int BASE_WATER_FOG_COLOUR = 329011;
//
//		public static final int OCEAN_WATER_COLOUR = 4159204;
//		public static final int OCEAN_WATER_FOG_COLOUR = 4159204;
//
//		public static final int LUKE_WARM_OCEAN_WATER_COLOUR = 4566514;
//		public static final int LUKE_WARM_OCEAN_WATER_FOG_COLOUR = 267827;
//
//		public static final int WARM_OCEAN_WATER_COLOUR = 4445678;
//		public static final int WARM_OCEAN_WATER_FOG_COLOUR = 270131;
//
//		public static final int COLD_OCEAN_WATER_COLOUR = 4020182;
//		public static final int COLD_OCEAN_WATER_FOG_COLOUR = 329011;
//
//		// Fog Colour
//		public static final int BASE_FOG_COLOUR = 12638463;
//
//		public static Biome.BiomeBuilder builder(Precipitation rain, BiomeCategory category, float downfall, float temperature, BiomeGenerationSettings settings, BiomeSpecialEffects ambience, MobSpawnSettings spawningInfo) {
//			return new Biome.BiomeBuilder().precipitation(rain).biomeCategory(category).downfall(downfall).temperature(temperature).generationSettings(settings).specialEffects(ambience).mobSpawnSettings(spawningInfo);
//		}
//	}
//}
