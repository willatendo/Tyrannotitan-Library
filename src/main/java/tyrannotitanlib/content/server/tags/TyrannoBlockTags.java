package tyrannotitanlib.content.server.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import tyrannotitanlib.content.TyrannoUtils;

public final class TyrannoBlockTags {
	public static final Tag.Named<Block> PLANT_PLACEABLE = tag("plant_placeables");

	private static Tag.Named<Block> tag(String id) {
		return BlockTags.createOptional(TyrannoUtils.rL(id));
	}
}
