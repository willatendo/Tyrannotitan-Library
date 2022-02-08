package core.content.tags;

import static core.content.Util.TYRANNO_UTILS;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public final class TyrannoBlockTags {
	public static final Tag.Named<Block> PLANT_PLACEABLE = tag("plant_placeables");

	private static Tag.Named<Block> tag(String id) {
		return BlockTags.createOptional(TYRANNO_UTILS.resource(id));
	}
}
