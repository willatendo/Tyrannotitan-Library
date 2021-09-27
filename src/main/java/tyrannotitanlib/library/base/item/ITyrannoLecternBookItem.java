package tyrannotitanlib.library.base.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tyrannotitanlib.library.network.Tyrannonetwork;
import tyrannotitanlib.library.network.packets.OpenLecternBookPacket;

public interface ITyrannoLecternBookItem 
{
	default boolean openLecternScreen(World world, BlockPos pos, PlayerEntity player, ItemStack book) 
	{
		Tyrannonetwork.INSTANCE.sendTo(new OpenLecternBookPacket(pos, book), player);
		return true;
	}
	
	void openLecternScreenClient(BlockPos pos, ItemStack book);
}
