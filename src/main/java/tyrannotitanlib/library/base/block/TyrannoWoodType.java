package tyrannotitanlib.library.base.block;

import net.minecraft.block.WoodType;

public class TyrannoWoodType extends WoodType
{
	protected TyrannoWoodType(String id) 
	{
		super(id);
	}

	public static final WoodType TEST = register(new TyrannoWoodType("test"));
}
