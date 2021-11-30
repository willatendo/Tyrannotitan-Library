package tyrannotitanlib.library.base.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class TyrannoCanyonWorldCarver extends CanyonWorldCarver
{
	public TyrannoCanyonWorldCarver(Codec<ProbabilityFeatureConfiguration> config, ImmutableSet<Block> set) 
	{
		super(config);
		this.replaceableBlocks = set;
	}	
}
