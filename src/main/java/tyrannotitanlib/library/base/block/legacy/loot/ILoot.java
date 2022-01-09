package tyrannotitanlib.library.base.block.legacy.loot;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

@Deprecated //Comming soon
public interface ILoot {
	public Item drop(BlockState state);
	
	public default int amount(BlockState state) {
		return 1;
	}
}
