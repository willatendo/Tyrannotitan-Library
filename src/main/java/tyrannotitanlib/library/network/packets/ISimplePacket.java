package tyrannotitanlib.library.network.packets;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public interface ISimplePacket 
{
	void encode(PacketBuffer buf);
	
	void handle(Supplier<NetworkEvent.Context> context);
}
