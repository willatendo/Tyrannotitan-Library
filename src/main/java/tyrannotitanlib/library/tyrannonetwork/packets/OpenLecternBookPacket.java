package tyrannotitanlib.library.tyrannonetwork.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import tyrannotitanlib.library.tyrannobook.item.ITyrannoLecternBookItem;

public class OpenLecternBookPacket implements IThreadSafePacket 
{
	private final BlockPos pos;
	private final ItemStack book;

	public OpenLecternBookPacket(FriendlyByteBuf buffer) 
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
	public void encode(FriendlyByteBuf buffer) 
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
