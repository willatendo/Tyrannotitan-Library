package tyrannotitanlib.tyranninetwork;

import net.minecraftforge.fml.network.NetworkDirection;
import tyrannotitanlib.tyrannibook.TyrannoUtils;
import tyrannotitanlib.tyranninetwork.packets.DropLecternBookPacket;
import tyrannotitanlib.tyranninetwork.packets.OpenLecternBookPacket;
import tyrannotitanlib.tyranninetwork.packets.UpdateHeldPagePacket;
import tyrannotitanlib.tyranninetwork.packets.UpdateLecturnPagePacket;

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
