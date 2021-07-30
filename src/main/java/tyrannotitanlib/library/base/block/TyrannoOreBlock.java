package tyrannotitanlib.library.base.block;

import java.util.Random;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

public class TyrannoOreBlock extends OreBlock
{
	public final int minXPDrop;
	public final int maxXPDrop;
	
	public TyrannoOreBlock(final Properties properties, final int minXPDrop, final int maxXPDrop) 
	{
		super(properties);
		this.minXPDrop = minXPDrop;
		this.maxXPDrop = maxXPDrop;
	}
	
	@Override
	protected int xpOnDrop(Random rand) 
	{
		return MathHelper.nextInt(rand, minXPDrop, maxXPDrop);
	}
}
