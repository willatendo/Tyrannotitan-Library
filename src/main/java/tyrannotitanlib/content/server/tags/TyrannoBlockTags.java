package tyrannotitanlib.content.server.tags;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import tyrannotitanlib.library.base.util.TyrannoUtils;

public final class TyrannoBlockTags 
{
	public static final ITag.INamedTag<Block> PLANT_PLACEABLE = tag("plant_placeables");
	
	private static ITag.INamedTag<Block> tag(String id)
	{
		return BlockTags.createOptional(TyrannoUtils.rL(id));
	}
}
