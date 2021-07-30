package tyrannotitanlib.library.base.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.block.Block;
import net.minecraft.world.gen.carver.UnderwaterCaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class TyrannoUnderwaterCaveWorldCarver extends UnderwaterCaveWorldCarver
{
	public TyrannoUnderwaterCaveWorldCarver(Codec<ProbabilityConfig> codec, ImmutableSet<Block> set) 
	{
		super(codec);
		this.replaceableBlocks = set;
	}
}
