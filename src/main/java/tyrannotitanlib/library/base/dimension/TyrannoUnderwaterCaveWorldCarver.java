package tyrannotitanlib.library.base.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.UnderwaterCaveWorldCarver;

public class TyrannoUnderwaterCaveWorldCarver extends UnderwaterCaveWorldCarver {
	public TyrannoUnderwaterCaveWorldCarver(Codec<CaveCarverConfiguration> codec, ImmutableSet<Block> set) {
		super(codec);
		this.replaceableBlocks = set;
	}
}
