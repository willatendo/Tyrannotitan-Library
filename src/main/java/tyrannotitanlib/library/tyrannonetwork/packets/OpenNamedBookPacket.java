package tyrannotitanlib.library.tyrannonetwork.packets;

import lombok.AllArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import tyrannotitanlib.library.tyrannobook.TyrannobookLoader;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;

@AllArgsConstructor
public class OpenNamedBookPacket implements IThreadsafePacket {
	private static final String BOOK_ERROR = "command.tyrannobook.book_test.not_found";
	private final ResourceLocation book;

	public OpenNamedBookPacket(FriendlyByteBuf buffer) {
		this.book = buffer.readResourceLocation();
	}

	@Override
	public void encode(FriendlyByteBuf buf) {
		buf.writeResourceLocation(book);
	}

	@Override
	public void handleThreadsafe(NetworkEvent.Context context) {
		TyrannobookData bookData = TyrannobookLoader.getBook(book);
		if (bookData != null) {
			bookData.openGui(new TextComponent("Book"), "", null, null);
		} else {
			ClientOnly.errorStatus(book);
		}
	}

	private static class ClientOnly {
		private static void errorStatus(ResourceLocation book) {
			Player player = Minecraft.getInstance().player;
			if (player != null) {
				player.displayClientMessage(new TranslatableComponent(BOOK_ERROR, book).withStyle(ChatFormatting.RED), false);
			}
		}
	}
}
