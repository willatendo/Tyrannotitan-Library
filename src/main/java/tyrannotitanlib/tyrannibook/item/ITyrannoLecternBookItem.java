package tyrannotitanlib.tyrannibook.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tyrannotitanlib.tyranninetwork.Tyrannonetwork;
import tyrannotitanlib.tyranninetwork.packets.OpenLecternBookPacket;

public interface ITyrannoLecternBookItem 
{
	default boolean openLecternScreen(World world, BlockPos pos, PlayerEntity player, ItemStack stack) 
	{
		Tyrannonetwork.INSTANCE.sendTo(new OpenLecternBookPacket(pos, stack), player);
		return true;
	}
	
	void openLecternScreenClient(BlockPos pos, ItemStack stack);
}
