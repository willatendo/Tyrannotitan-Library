package tyrannotitanlib.library.compatibility.decorativeblocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public interface IDecorativeBlocksWoodType 
{
	String toString();

	Block getLog();

	Block getStrippedLog();

	Block getSlab();

	Block getFence();

	Block getPlanks();

	default MaterialColor getMaterialColor() 
	{
		return MaterialColor.WOOD;
	}

	default Material getMaterial() 
	{
		return Material.WOOD;
	}

	default SoundType getSoundType() 
	{
		return SoundType.WOOD;
	}

	boolean isAvailable();

	boolean isFlammable();
}
