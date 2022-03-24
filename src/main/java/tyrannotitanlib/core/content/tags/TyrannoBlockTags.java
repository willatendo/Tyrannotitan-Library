package tyrannotitanlib.core.content.tags;

import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class TyrannoBlockTags {
	public static final TagKey<Block> PLANT_PLACEABLE = tag("plant_placeables");

	private static TagKey<Block> tag(String id) {
		return TagKey.create(Registry.BLOCK_REGISTRY, TYRANNO_UTILS.mod(id));
	}
}
