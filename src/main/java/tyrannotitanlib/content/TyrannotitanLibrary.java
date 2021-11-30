package tyrannotitanlib.content;

import static tyrannotitanlib.library.utils.TyrannoUtils.TYRANNO_ID;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import tyrannotitanlib.content.client.Capes;
import tyrannotitanlib.content.config.TyrannotitanConfig;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.content.server.init.TyrannoRegistries;
import tyrannotitanlib.library.base.block.TyrannoBeehiveBlock;
import tyrannotitanlib.library.base.block.TyrannoLogBlock;
import tyrannotitanlib.library.base.block.TyrannoSignManager;
import tyrannotitanlib.library.base.item.TyrannoSpawnEggItem;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookLoader;
import tyrannotitanlib.library.tyrannobook.item.TyrannobookItem;
import tyrannotitanlib.library.tyrannomation.network.TyrannomationNetwork;
import tyrannotitanlib.library.tyrannomation.resource.ResourceListener;
import tyrannotitanlib.library.tyrannonetwork.Tyrannonetwork;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;
import tyrannotitanlib.library.tyrannores.world.OreGeneration;
import tyrannotitanlib.library.utils.TyrannoUtils;

@Mod(TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public static volatile boolean hasInitialized;
	
	public TyrannotitanLibrary() 
	{
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		final IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		
		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		
		TyrannoRegistries.register();
		initTyrannomation();
				
		forgeBus.addListener(EventPriority.HIGH, OreGeneration::addOresToOverworld);
		
		forgeBus.register(new TyrannoRegister());
		
		ModLoadingContext.get().registerConfig(Type.SERVER, TyrannotitanConfig.serverConfig);
		
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, PlayerInteractEvent.RightClickBlock.class, TyrannobookItem::interactWithBlock);
	}
	
	private static void initTyrannomation() 
	{
		if(!hasInitialized) 
		{
			DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ResourceListener::registerReloadListener);
			TyrannomationNetwork.initialize();
		}
		hasInitialized = true;
	}
	
	private void commonSetup(final FMLCommonSetupEvent event) 
	{
		BufferedReader urlContents = TyrannoUtils.getURLContents("https://raw.githubusercontent.com/Willatendo/Tyrannotitan-Library/master/src/main/resources/assets/tyrannotitanlib/tyrannotitan.txt", "assets/tyrannotitanlib/tyrannotitan.txt");
		if(urlContents != null) 
		{
			try 
			{
				String line;
				while((line = urlContents.readLine()) != null) 
				{
					TyrannoUtils.TYRANNOTITANS.add(line);
				}
			} 
			catch(IOException e) 
			{
				TyrannoUtils.LOGGER.warn("Failed to load tyrannotitan member's capes");
			}
		} 
		else
		{
			TyrannoUtils.LOGGER.warn("Failed to load tyrannotitan member's capes");
		}

		Tyrannonetwork.registerPackets();
		
		TyrannoLogBlock.addStripping();
		
		event.enqueueWork(() -> 
		{
			this.addBeehivePOI();	
		});
		
		event.enqueueWork(() -> 
		{
			ImmutableSet.Builder<Block> builder = ImmutableSet.builder();
			builder.addAll(BlockEntityType.SIGN.validBlocks);
			TyrannoSignManager.forEachSignBlock(builder::add);
			BlockEntityType.SIGN.validBlocks = builder.build();
		});
	}
	
	private void clientSetup(FMLClientSetupEvent event)
	{
		final IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.register(new Capes());

		ResourceManager manager = Minecraft.getInstance().getResourceManager();
		if(manager instanceof ReloadableResourceManager) 
		{
			((ReloadableResourceManager)manager).registerReloadListener(new TyrannobookLoader());
		}
	}
	
	@SubscribeEvent
	public static void entityRegistry(RegistryEvent.Register<EntityType<?>> event) 
	{
		TyrannoSpawnEggItem.initSpawnEggs();
	}
	
	private void addBeehivePOI() 
	{
		PoiType.BEEHIVE.matchingStates = Sets.newHashSet(PoiType.BEEHIVE.matchingStates);
		Map<BlockState, PoiType> statePointOfInterestMap = ObfuscationReflectionHelper.getPrivateValue(PoiType.class, null, "field_221073_u");
		if(statePointOfInterestMap != null) 
		{
			for(Block block : TyrannoBlockEntities.collectBlocks(TyrannoBeehiveBlock.class)) 
			{
				block.getStateDefinition().getPossibleStates().forEach(state -> 
				{
					statePointOfInterestMap.put(state, PoiType.BEEHIVE);
					PoiType.BEEHIVE.matchingStates.add(state);
				});
			}
		}
	}
}
