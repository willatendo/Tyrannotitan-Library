package tyrannotitanlib.library.base.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.UnderwaterCaveWorldCarver;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class TyrannoUnderwaterCaveWorldCarver extends UnderwaterCaveWorldCarver
{
	public TyrannoUnderwaterCaveWorldCarver(Codec<ProbabilityFeatureConfiguration> codec, ImmutableSet<Block> set) 
	{
		super(codec);
		this.replaceableBlocks = set;
	}
}
