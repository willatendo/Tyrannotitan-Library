package tyrannotitanlib.library.tyrannotitanlib.dimension.generators;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class SeedNoiseChunkGenerator extends NoiseBasedChunkGenerator {
	public SeedNoiseChunkGenerator(Registry<NoiseParameters> registry, BiomeSource source, long seed, Supplier<NoiseGeneratorSettings> settings) {
		super(registry, source, seed, settings);
	}
}
