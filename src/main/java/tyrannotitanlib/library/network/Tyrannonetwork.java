package tyrannotitanlib.library.network;

import net.minecraftforge.fml.network.NetworkDirection;
import tyrannotitanlib.library.base.utils.TyrannoUtils;
import tyrannotitanlib.library.network.packets.DropLecternBookPacket;
import tyrannotitanlib.library.network.packets.OpenLecternBookPacket;
import tyrannotitanlib.library.network.packets.UpdateHeldPagePacket;
import tyrannotitanlib.library.network.packets.UpdateLecturnPagePacket;

public class Tyrannonetwork 
{
	public static final NetworkWrapper INSTANCE = new NetworkWrapper(TyrannoUtils.rL("network"));
	
	public static void registerPackets() 
	{
		INSTANCE.registerPacket(OpenLecternBookPacket.class, OpenLecternBookPacket::new, NetworkDirection.PLAY_TO_CLIENT);
		INSTANCE.registerPacket(UpdateLecturnPagePacket.class, UpdateLecturnPagePacket::new, NetworkDirection.PLAY_TO_SERVER);
		INSTANCE.registerPacket(UpdateHeldPagePacket.class, UpdateHeldPagePacket::new, NetworkDirection.PLAY_TO_SERVER);
		INSTANCE.registerPacket(DropLecternBookPacket.class, DropLecternBookPacket::new, NetworkDirection.PLAY_TO_SERVER);
	}
}
