package tyrannotitanlib.library.base.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class TyrannoRotatedPillerOreBlock extends RotatedPillarBlock
{
	public final int minXPDrop;
	public final int maxXPDrop;
	
	public TyrannoRotatedPillerOreBlock(final Properties properties, final int minXPDrop, final int maxXPDrop) 
	{
		super(properties);
		this.minXPDrop = minXPDrop;
		this.maxXPDrop = maxXPDrop;
	}
	
	protected int xpOnDrop(Random rand) 
	{
		return MathHelper.nextInt(rand, minXPDrop, maxXPDrop);
	}
	
	@Override
	public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) 
	{
		return silktouch == 0 ? this.xpOnDrop(RANDOM) : 0;
	}
}
