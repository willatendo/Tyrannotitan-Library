package tyrannotitanlib.tyrannibook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tyrannotitanlib.tyranninetwork.Tyrannonetwork;
import tyrannotitanlib.tyranninetwork.packets.OpenLecternBookPacket;

public interface ILecternBookItem {
	default boolean openLecternScreen(Level world, BlockPos pos, Player player, ItemStack book) {
		Tyrannonetwork.INSTANCE.sendTo(new OpenLecternBookPacket(pos, book), player);
		return true;
	}

	void openLecternScreenClient(BlockPos pos, ItemStack book);
}
