package tyrannotitanlib.library.tyrannonetwork.packets;

import lombok.RequiredArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;
import tyrannotitanlib.library.tyrannobook.TyrannobookHelper;

@RequiredArgsConstructor
public class UpdateHeldPagePacket implements IThreadsafePacket {
	private final InteractionHand hand;
	private final String page;

	public UpdateHeldPagePacket(FriendlyByteBuf buffer) {
		this.hand = buffer.readEnum(InteractionHand.class);
		this.page = buffer.readUtf(100);
	}

	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeEnum(hand);
		buf.writeUtf(this.page);
	}

	@Override
	public void handleThreadsafe(Context context) {
		Player player = context.getSender();
		if (player != null && this.page != null) {
			ItemStack stack = player.getItemInHand(hand);
			if (!stack.isEmpty()) {
				TyrannobookHelper.writeSavedPageToBook(stack, this.page);
			}
		}
	}
}
