package tyrannotitanlib.library.tyrannonetwork.packets;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public interface ISimplePacket {
	void encode(FriendlyByteBuf buf);

	void handle(Supplier<NetworkEvent.Context> context);
}
