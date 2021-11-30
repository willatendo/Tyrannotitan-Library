package tyrannotitanlib.library.base.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class TyrannoCaveWorldCarver extends CaveWorldCarver
{
	public TyrannoCaveWorldCarver(Codec<ProbabilityFeatureConfiguration> config, int height, ImmutableSet<Block> set) 
	{
		super(config, height);
		this.replaceableBlocks = set;
	}
}
