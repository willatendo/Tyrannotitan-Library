package tyrannotitanlib.tyranninetwork.packets;

import java.util.function.Supplier;

import net.minecraftforge.network.NetworkEvent;

public interface IThreadsafePacket extends ISimplePacket {
	@Override
	default void handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> handleThreadsafe(context));
		context.setPacketHandled(true);
	}

	void handleThreadsafe(NetworkEvent.Context context);
}
