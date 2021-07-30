package tyrannotitanlib.library.base.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.block.Block;
import net.minecraft.world.gen.carver.CanyonWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class TyrannoCanyonWorldCarver extends CanyonWorldCarver
{
	public TyrannoCanyonWorldCarver(Codec<ProbabilityConfig> config, ImmutableSet<Block> set) 
	{
		super(config);
		this.replaceableBlocks = set;
	}	
}
