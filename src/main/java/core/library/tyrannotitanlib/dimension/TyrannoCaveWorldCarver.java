package core.library.tyrannotitanlib.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

public class TyrannoCaveWorldCarver extends CaveWorldCarver {
	public TyrannoCaveWorldCarver(Codec<CaveCarverConfiguration> config, ImmutableSet<Block> set) {
		super(config);
		this.replaceableBlocks = set;
	}
}
