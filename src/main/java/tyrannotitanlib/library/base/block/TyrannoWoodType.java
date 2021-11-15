package tyrannotitanlib.library.base.block;

import net.minecraft.block.WoodType;

public class TyrannoWoodType extends WoodType
{
	public TyrannoWoodType(String id) 
	{
		super(id);
	}
	
	public static final WoodType CUSTOM = register(new TyrannoWoodType("custom"));
}
