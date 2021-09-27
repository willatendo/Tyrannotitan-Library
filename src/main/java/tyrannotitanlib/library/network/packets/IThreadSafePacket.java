package tyrannotitanlib.library.network.packets;

import java.util.function.Supplier;

import net.minecraftforge.fml.network.NetworkEvent;

public interface IThreadSafePacket extends ISimplePacket 
{
	@Override
	default void handle(Supplier<NetworkEvent.Context> supplier) 
	{
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> handleThreadsafe(context));
		context.setPacketHandled(true);
	}

	void handleThreadsafe(NetworkEvent.Context context);
}
