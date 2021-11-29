package tyrannotitanlib.library.tyrannores.world;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import tyrannotitanlib.content.config.TyrannotitanConfig;
import tyrannotitanlib.content.server.init.TyrannoBlocks;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoreConfiguredFeatures 
{
	public static final ConfiguredFeature<?, ?> COPPER_ORE = register("copper_ore", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, TyrannoBlocks.COPPER_ORE.defaultBlockState(), TyrannotitanConfig.SERVER_CONFIG.copperVeinSize.get())).range(TyrannotitanConfig.SERVER_CONFIG.copperRange.get()).squared().count(TyrannotitanConfig.SERVER_CONFIG.copperCountPerChunk.get()));

	public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) 
	{
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, TyrannoUtils.rL(id), configuredFeature);
	}
}
