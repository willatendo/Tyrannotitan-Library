package tyrannotitanlib.library.compatibility.decorativeblocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public interface IDecorativeBlocksWoodType {
	String toString();

	Block getLog();

	Block getStrippedLog();

	Block getSlab();

	Block getFence();

	Block getPlanks();

	default MaterialColor getMaterialColor() {
		return MaterialColor.WOOD;
	}

	default Material getMaterial() {
		return Material.WOOD;
	}

	default SoundType getSoundType() {
		return SoundType.WOOD;
	}

	boolean isAvailable();

	boolean isFlammable();
}
