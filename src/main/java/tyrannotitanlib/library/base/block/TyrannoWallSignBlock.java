package tyrannotitanlib.library.base.block;

import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;

public class TyrannoWallSignBlock extends WallSignBlock
{
	public TyrannoWallSignBlock(Properties properties, WoodType woodType) 
	{
		super(properties, woodType);
		
		TyrannoSignManager.registerSignBlock(() -> this);
	}
}
