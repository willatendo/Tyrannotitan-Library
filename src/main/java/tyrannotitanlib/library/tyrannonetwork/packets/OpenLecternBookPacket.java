package tyrannotitanlib.library.tyrannonetwork.packets;

import lombok.AllArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import tyrannotitanlib.library.tyrannobook.ILecternBookItem;

@AllArgsConstructor
public class OpenLecternBookPacket implements IThreadsafePacket {
	private final BlockPos pos;
	private final ItemStack book;

	public OpenLecternBookPacket(FriendlyByteBuf buffer) {
		this.pos = buffer.readBlockPos();
		this.book = buffer.readItem();
	}

	@Override
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeBlockPos(pos);
		buffer.writeItem(book);
	}

	@Override
	public void handleThreadsafe(Context context) {
		if (book.getItem() instanceof ILecternBookItem) {
			((ILecternBookItem) book.getItem()).openLecternScreenClient(pos, book);
		}
	}
}
