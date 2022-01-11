package tyrannotitanlib.library.tyrannonetwork.packets;

import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import tyrannotitanlib.library.tyrannonetwork.Tyrannonetwork;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class SyncTyrannomationPacket {
	private final String key;
	private final int id;
	private final int state;

	public SyncTyrannomationPacket(String key, int id, int state) {
		this.key = key;
		this.id = id;
		this.state = state;
	}

	public static void register(SimpleChannel channel, int id) {
		channel.registerMessage(id, SyncTyrannomationPacket.class, SyncTyrannomationPacket::encode, SyncTyrannomationPacket::decode, SyncTyrannomationPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
	}

	private static SyncTyrannomationPacket decode(FriendlyByteBuf buf) {
		final String key = buf.readUtf();
		final int id = buf.readVarInt();
		final int state = buf.readVarInt();
		return new SyncTyrannomationPacket(key, id, state);
	}

	private void encode(FriendlyByteBuf buf) {
		buf.writeUtf(key);
		buf.writeVarInt(id);
		buf.writeVarInt(state);
	}

	private void handle(Supplier<NetworkEvent.Context> sup) {
		final NetworkEvent.Context ctx = sup.get();
		ctx.enqueueWork(() -> {
			final ISyncable syncable = Tyrannonetwork.getSyncable(key);
			if (syncable != null) {
				syncable.onAnimationSync(id, state);
			} else {
				TyrannoUtils.LOGGER.warn("Syncable on the server is missing on the client for " + key);
			}
		});
		ctx.setPacketHandled(true);
	}
}
