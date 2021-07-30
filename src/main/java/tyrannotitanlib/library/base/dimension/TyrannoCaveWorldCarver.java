package tyrannotitanlib.library.base.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.block.Block;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class TyrannoCaveWorldCarver extends CaveWorldCarver
{
	public TyrannoCaveWorldCarver(Codec<ProbabilityConfig> config, int height, ImmutableSet<Block> set) 
	{
		super(config, height);
		this.replaceableBlocks = set;
	}
}
