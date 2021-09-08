package tyrannotitanlib.library.base.util;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class TyrannoSignManager 
{
	private static final Set<WoodType> WOOD_TYPES = new HashSet<>();
	
	@OnlyIn(Dist.CLIENT)
	public static void setupAtlas()
	{
		for(WoodType type : WOOD_TYPES)
		{
			Atlases.addWoodType(type);
		}
	}
	
	public static synchronized WoodType registerWoodType(WoodType type)
	{
		WOOD_TYPES.add(type);
		return WoodType.register(type);
	}
}
