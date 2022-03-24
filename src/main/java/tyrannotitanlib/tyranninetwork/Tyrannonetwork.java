package tyrannotitanlib.tyranninetwork;

import static tyrannotitanlib.core.content.ModUtilities.LOG;
import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkInstance;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IRegistryDelegate;
import tyrannotitanlib.tyranninetwork.packets.DropLecternBookPacket;
import tyrannotitanlib.tyranninetwork.packets.ISyncable;
import tyrannotitanlib.tyranninetwork.packets.OpenLecternBookPacket;
import tyrannotitanlib.tyranninetwork.packets.OpenNamedBookPacket;
import tyrannotitanlib.tyranninetwork.packets.SyncTyrannomationPacket;
import tyrannotitanlib.tyranninetwork.packets.UpdateHeldPagePacket;
import tyrannotitanlib.tyranninetwork.packets.UpdateLecternPagePacket;

public class Tyrannonetwork {
	public static final NetworkWrapper INSTANCE = new NetworkWrapper(TYRANNO_UTILS.mod("network"));
	private static final Map<String, Supplier<ISyncable>> SYNCABLES = new HashMap<>();

	private static final String PROTOCOL_VERSION = "0";
	private static final SimpleChannel CHANNEL = fetchGeckoLibChannel("main");

	private static SimpleChannel fetchGeckoLibChannel(String name) {
		try {
			final ResourceLocation key = TYRANNO_UTILS.mod(name);
			final Method findTarget = NetworkRegistry.class.getDeclaredMethod("findTarget", ResourceLocation.class);
			findTarget.setAccessible(true);
			return ((Optional<NetworkInstance>) findTarget.invoke(null, key)).map(SimpleChannel::new).orElseGet(() -> NetworkRegistry.newSimpleChannel(key, () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals));
		} catch (Throwable t) {
			throw new RuntimeException("Failed to fetch TyrannotitanLib network channel", t);
		}
	}

	public static void initialize() {
		int id = -1;

		SyncTyrannomationPacket.register(CHANNEL, ++id);
	}

	public static void syncAnimation(PacketDistributor.PacketTarget target, ISyncable syncable, int id, int state) {
		if (!target.getDirection().getOriginationSide().isServer()) {
			throw new IllegalArgumentException("Only the server can request animation syncs!");
		}

		final String key = syncable.getSyncKey();

		if (!SYNCABLES.containsKey(key)) {
			throw new IllegalArgumentException("Syncable not registered for " + key);
		}
		CHANNEL.send(target, new SyncTyrannomationPacket(key, id, state));
	}

	public static ISyncable getSyncable(String key) {
		final Supplier<ISyncable> delegate = SYNCABLES.get(key);
		return delegate == null ? null : delegate.get();
	}

	public static <E extends ForgeRegistryEntry<E>, T extends ForgeRegistryEntry<E> & ISyncable> void registerSyncable(T entry) {
		final IRegistryDelegate<?> delegate = entry.delegate;
		final String key = entry.getSyncKey();
		if (SYNCABLES.putIfAbsent(key, () -> (ISyncable) delegate.get()) != null) {
			throw new IllegalArgumentException("Syncable already registered for " + key);
		}
		LOG.debug("Registered syncable for " + key);
	}

	public static void registerPackets() {
		INSTANCE.registerPacket(OpenLecternBookPacket.class, OpenLecternBookPacket::new, NetworkDirection.PLAY_TO_CLIENT);
		INSTANCE.registerPacket(UpdateHeldPagePacket.class, UpdateHeldPagePacket::new, NetworkDirection.PLAY_TO_SERVER);
		INSTANCE.registerPacket(UpdateLecternPagePacket.class, UpdateLecternPagePacket::new, NetworkDirection.PLAY_TO_SERVER);
		INSTANCE.registerPacket(DropLecternBookPacket.class, DropLecternBookPacket::new, NetworkDirection.PLAY_TO_SERVER);
		INSTANCE.registerPacket(OpenNamedBookPacket.class, OpenNamedBookPacket::new, NetworkDirection.PLAY_TO_CLIENT);
	}
}
