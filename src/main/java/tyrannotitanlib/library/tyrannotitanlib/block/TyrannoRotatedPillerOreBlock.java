package tyrannotitanlib.library.tyrannotitanlib.block;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TyrannoRotatedPillerOreBlock extends RotatedPillarBlock {
	public final int minXPDrop;
	public final int maxXPDrop;

	public TyrannoRotatedPillerOreBlock(Properties properties, int minXPDrop, int maxXPDrop) {
		super(properties);
		this.minXPDrop = minXPDrop;
		this.maxXPDrop = maxXPDrop;
	}

	protected int xpOnDrop(Random rand) {
		return Mth.nextInt(rand, minXPDrop, maxXPDrop);
	}

	@Override
	public int getExpDrop(BlockState state, LevelReader reader, BlockPos pos, int fortune, int silktouch) {
		return silktouch == 0 ? this.xpOnDrop(RANDOM) : 0;
	}
}
