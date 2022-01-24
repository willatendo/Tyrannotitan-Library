package tyrannotitanlib.content.server;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ServerConfig {
	public final BooleanValue replaceDefaultWorldtypes;
	public final BooleanValue replaceDefaultNether;
	public final IntValue overworldRegionSize;
	public final IntValue overworldLargeBiomesRegionSize;
	public final IntValue netherRegionSize;
	public final IntValue netherLargeBiomesRegionSize;
	public final IntValue vanillaOverworldRegionWeight;
	public final IntValue vanillaNetherRegionWeight;
	public final IntValue datapackOverworldRegionWeight;
	public final IntValue datapackNetherRegionWeight;
	public final BooleanValue forceResetBiomeParameters;

	public ServerConfig(ForgeConfigSpec.Builder builder) {
		this.replaceDefaultWorldtypes = builder.comment("Whether to replace the default built-in world types with our own.").define("replaceDefaultWorldtypes", true);
		this.replaceDefaultNether = builder.comment("Whether to replace the default Nether with our own.").define("replaceDefaultNether", true);
		this.overworldRegionSize = builder.comment("The size of overworld biome regions from each mod that uses TyrannoBlender.").defineInRange("overworldRegionSize", 3, 2, 6);
		this.overworldLargeBiomesRegionSize = builder.comment("The size of overworld biome regions from each mod that uses TyrannoBlender when using the large biomes world type.").defineInRange("overworldLargeBiomesRegionSize", 5, 2, 6);
		this.netherRegionSize = builder.comment("The size of nether biome regions from each mod that uses TyrannoBlender.").defineInRange("nether_region_size", 2, 2, 6);
		this.netherLargeBiomesRegionSize = builder.comment("The size of nether biome regions from each mod that uses TyrannoBlender when using the large biomes world type.").defineInRange("nether_large_biomes_region_size", 4, 2, 6);
		this.vanillaOverworldRegionWeight = builder.comment("The weighting of vanilla biome regions in the overworld.").defineInRange("vanilla_overworld_region_weight", 10, 0, Integer.MAX_VALUE);
		this.vanillaNetherRegionWeight = builder.comment("The weighting of vanilla biome regions in the nether.").defineInRange("vanilla_nether_region_weight", 10, 0, Integer.MAX_VALUE);
		this.datapackOverworldRegionWeight = builder.comment("The weighting of data pack biome regions in the overworld.").defineInRange("datapack_overworld_region_weight", 15, 0, Integer.MAX_VALUE);
		this.datapackNetherRegionWeight = builder.comment("The weighting of data pack biome regions in the nether.").defineInRange("datapack_nether_region_weight", 15, 0, Integer.MAX_VALUE);
		this.forceResetBiomeParameters = builder.comment("Force the biome parameters to reset when loading worlds.").define("force_reset_biome_parameters", false);
	}
}
