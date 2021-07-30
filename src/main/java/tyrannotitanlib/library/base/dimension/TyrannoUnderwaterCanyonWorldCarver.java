package tyrannotitanlib.library.base.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.block.Block;
import net.minecraft.world.gen.carver.UnderwaterCanyonWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class TyrannoUnderwaterCanyonWorldCarver extends UnderwaterCanyonWorldCarver
{
	public TyrannoUnderwaterCanyonWorldCarver(Codec<ProbabilityConfig> codec, ImmutableSet<Block> set) 
	{
		super(codec);
		this.replaceableBlocks = set;
	}
}
