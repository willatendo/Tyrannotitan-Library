package tyrannotitanlib.library.base.block;

import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;

public class TyrannoStandingSignBlock extends StandingSignBlock
{
	public TyrannoStandingSignBlock(Properties properties, WoodType woodType) 
	{
		super(properties, woodType);
		
		TyrannoSignManager.registerSignBlock(() -> this);
	}
}
