package core.library.tyrannotitanlib.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;

public class TyrannoCanyonWorldCarver extends CanyonWorldCarver {
	public TyrannoCanyonWorldCarver(Codec<CanyonCarverConfiguration> config, ImmutableSet<Block> set) {
		super(config);
		this.replaceableBlocks = set;
	}
}
