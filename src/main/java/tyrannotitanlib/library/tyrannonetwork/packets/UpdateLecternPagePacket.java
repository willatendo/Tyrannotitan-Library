package tyrannotitanlib.library.tyrannonetwork.packets;

import lombok.AllArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;
import tyrannotitanlib.library.base.block.entity.BlockEntityHelper;
import tyrannotitanlib.library.tyrannobook.TyrannobookHelper;

@AllArgsConstructor
public class UpdateLecternPagePacket implements IThreadsafePacket {
	private final BlockPos pos;
	private final String page;

	public UpdateLecternPagePacket(FriendlyByteBuf buffer) {
		this.pos = buffer.readBlockPos();
		this.page = buffer.readUtf(100);
	}

	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
		buf.writeUtf(page);
	}

	@Override
	public void handleThreadsafe(Context context) {
		Player player = context.getSender();
		if (player != null && this.page != null) {
			Level world = player.getCommandSenderWorld();
			BlockEntityHelper.get(LecternBlockEntity.class, world, this.pos).ifPresent(te -> {
				ItemStack stack = te.getBook();
				if (!stack.isEmpty()) {
					TyrannobookHelper.writeSavedPageToBook(stack, this.page);
				}
			});
		}
	}
}
