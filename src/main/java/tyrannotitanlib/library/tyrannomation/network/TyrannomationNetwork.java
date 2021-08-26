package tyrannotitanlib.library.tyrannomation.network;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkInstance;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IRegistryDelegate;
import tyrannotitanlib.library.base.util.TyrannoUtils;
import tyrannotitanlib.library.tyrannomation.network.messages.SyncTyrannomationMsg;

public class TyrannomationNetwork 
{
	private static final Map<String, Supplier<ISyncable>> SYNCABLES = new HashMap<>();

	private static final String PROTOCOL_VERSION = "0";
	private static final SimpleChannel CHANNEL = fetchGeckoLibChannel("main");

	private static SimpleChannel fetchGeckoLibChannel(String name) 
	{
		try 
		{
			final ResourceLocation key = new ResourceLocation(TyrannoUtils.TYRANNO_ID, name);
			final Method findTarget = NetworkRegistry.class.getDeclaredMethod("findTarget", ResourceLocation.class);
			findTarget.setAccessible(true);
			return ((Optional<NetworkInstance>) findTarget.invoke(null, key)).map(SimpleChannel::new).orElseGet(() -> NetworkRegistry.newSimpleChannel(key, () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals));
		} 
		catch(Throwable t) 
		{
			throw new RuntimeException("Failed to fetch TyrannotitanLib network channel", t);
		}
	}

	public static void initialize() 
	{
		int id = -1;

		SyncTyrannomationMsg.register(CHANNEL, ++id);
	}

	public static void syncAnimation(PacketDistributor.PacketTarget target, ISyncable syncable, int id, int state) 
	{
		if(!target.getDirection().getOriginationSide().isServer()) 
		{
			throw new IllegalArgumentException("Only the server can request animation syncs!");
		}
		
		final String key = syncable.getSyncKey();
		
		if(!SYNCABLES.containsKey(key)) 
		{
			throw new IllegalArgumentException("Syncable not registered for " + key);
		}
		CHANNEL.send(target, new SyncTyrannomationMsg(key, id, state));
	}

	public static ISyncable getSyncable(String key) 
	{
		final Supplier<ISyncable> delegate = SYNCABLES.get(key);
		return delegate == null ? null : delegate.get();
	}

	public static <E extends ForgeRegistryEntry<E>, T extends ForgeRegistryEntry<E> & ISyncable> void registerSyncable(T entry) 
	{
		final IRegistryDelegate<?> delegate = entry.delegate;
		final String key = entry.getSyncKey();
		if(SYNCABLES.putIfAbsent(key, () -> (ISyncable) delegate.get()) != null) 
		{
			throw new IllegalArgumentException("Syncable already registered for " + key);
		}
		TyrannoUtils.LOGGER.debug("Registered syncable for " + key);
	}
}
