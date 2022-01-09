package tyrannotitanlib.library.base.block.legacy.tool;

import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

@Deprecated //Comming soon
public interface IToolHarvestable {
	public Tier toolType(BlockState state);
}
