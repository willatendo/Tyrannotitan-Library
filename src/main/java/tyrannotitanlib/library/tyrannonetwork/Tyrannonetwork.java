package tyrannotitanlib.library.tyrannonetwork;

import net.minecraftforge.fmllegacy.network.NetworkDirection;
import tyrannotitanlib.library.tyrannonetwork.packets.DropLecternBookPacket;
import tyrannotitanlib.library.tyrannonetwork.packets.OpenLecternBookPacket;
import tyrannotitanlib.library.tyrannonetwork.packets.OpenNamedBookPacket;
import tyrannotitanlib.library.tyrannonetwork.packets.UpdateHeldPagePacket;
import tyrannotitanlib.library.tyrannonetwork.packets.UpdateLecternPagePacket;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class Tyrannonetwork {
	public static final NetworkWrapper INSTANCE = new NetworkWrapper(TyrannoUtils.rL("network"));

	public static void registerPackets() {
		INSTANCE.registerPacket(OpenLecternBookPacket.class, OpenLecternBookPacket::new, NetworkDirection.PLAY_TO_CLIENT);
		INSTANCE.registerPacket(UpdateHeldPagePacket.class, UpdateHeldPagePacket::new, NetworkDirection.PLAY_TO_SERVER);
		INSTANCE.registerPacket(UpdateLecternPagePacket.class, UpdateLecternPagePacket::new, NetworkDirection.PLAY_TO_SERVER);
		INSTANCE.registerPacket(DropLecternBookPacket.class, DropLecternBookPacket::new, NetworkDirection.PLAY_TO_SERVER);
		INSTANCE.registerPacket(OpenNamedBookPacket.class, OpenNamedBookPacket::new, NetworkDirection.PLAY_TO_CLIENT);
	}
}
