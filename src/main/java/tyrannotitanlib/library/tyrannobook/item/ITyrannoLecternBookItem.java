package tyrannotitanlib.library.tyrannobook.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import tyrannotitanlib.library.tyrannonetwork.Tyrannonetwork;
import tyrannotitanlib.library.tyrannonetwork.packets.OpenLecternBookPacket;

public interface ITyrannoLecternBookItem {
	default boolean openLecternScreen(Level world, BlockPos pos, Player player, ItemStack stack) {
		Tyrannonetwork.INSTANCE.sendTo(new OpenLecternBookPacket(pos, stack), player);
		return true;
	}

	void openLecternScreenClient(BlockPos pos, ItemStack stack);
}
