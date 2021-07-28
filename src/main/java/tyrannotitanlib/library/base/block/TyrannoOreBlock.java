package tyrannotitanlib.library.base.block;

import java.util.Random;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

/*
 * This makes it so you can add an xp drop to an ore.
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

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
