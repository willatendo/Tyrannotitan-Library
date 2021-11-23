package tyrannotitanlib.library.tyrannonetwork.packets;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import tyrannotitanlib.library.tyrannobook.item.ITyrannoLecternBookItem;

public class OpenLecternBookPacket implements IThreadSafePacket 
{
	private final BlockPos pos;
	private final ItemStack book;

	public OpenLecternBookPacket(PacketBuffer buffer) 
	{
		this.pos = buffer.readBlockPos();
		this.book = buffer.readItem();
	}

	public OpenLecternBookPacket(BlockPos pos, ItemStack book) 
	{
		this.pos = pos;
		this.book = book;
	}

	@Override
	public void encode(PacketBuffer buffer) 
	{
		buffer.writeBlockPos(pos);
		buffer.writeItem(book);
	}

	@Override
	public void handleThreadsafe(Context context) 
	{
		if(book.getItem() instanceof ITyrannoLecternBookItem) 
		{
			((ITyrannoLecternBookItem) book.getItem()).openLecternScreenClient(pos, book);
		}
	}
}
