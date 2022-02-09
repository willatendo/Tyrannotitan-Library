package tyrannotitanlib.core.content;

import static tyrannotitanlib.core.content.Util.LOG;
import static tyrannotitanlib.core.content.Util.TYRANNO_ID;
import static tyrannotitanlib.core.content.Util.collectBlocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import tyrannotitanlib.core.client.Capes;
import tyrannotitanlib.core.content.init.TyrannoBanners;
import tyrannotitanlib.core.content.init.TyrannoBlockEntities;
import tyrannotitanlib.core.content.init.TyrannoEntities;
import tyrannotitanlib.core.content.init.TyrannoItems;
import tyrannotitanlib.library.block.TyrannoBeehiveBlock;
import tyrannotitanlib.library.block.TyrannoLogBlock;
import tyrannotitanlib.library.block.TyrannoSignManager;
import tyrannotitanlib.library.compatibility.CompatibilityRegistries;
import tyrannotitanlib.tyrannibook.TyrannobookLoader;
import tyrannotitanlib.tyrannimation.resource.ResourceListener;
import tyrannotitanlib.tyranninetwork.Tyrannonetwork;
import tyrannotitanlib.tyranniregister.TyrannoRegister;

@Mod(TYRANNO_ID)
@EventBusSubscriber(bus = Bus.MOD, modid = TYRANNO_ID)
public class TyrannotitanLibrary {
	public static List<String> TYRANNOTITANS = new ArrayList<>();
	public static volatile boolean hasInitializedTyrannimation;
	
	public TyrannotitanLibrary() {
		var serverModBus = FMLJavaModLoadingContext.get().getModEventBus();
		var serverForgeBus = MinecraftForge.EVENT_BUS;

		serverModBus.addListener(this::commonSetup);
		serverModBus.addListener(this::clientSetup);
		serverModBus.addListener(this::listenersSetup);

		TyrannoBanners.init();
		TyrannoBlockEntities.init();
		TyrannoEntities.init();
		TyrannoItems.init();
		
		CompatibilityRegistries.init();
		initTyrannimation();

		serverForgeBus.register(new TyrannoRegister());
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		// Gets all the usernames of Tyrannotitan Members and gives them a cape.
		var urlContents = Util.getURLContents("https://raw.githubusercontent.com/Willatendo/Tyrannotitan-Library/master/src/main/resources/assets/tyrannotitanlib/tyrannotitan.txt", "assets/tyrannotitanlib/tyrannotitan.txt");
		if (urlContents != null) {
			try {
				String line;
				while ((line = urlContents.readLine()) != null) {
					TYRANNOTITANS.add(line);
				}
			} catch (IOException e) {
				LOG.warn("Failed to load tyrannotitan member's capes");
			}
		} else {
			LOG.warn("Failed to load tyrannotitan member's capes");
		}

		// Registers the packets used for Server-to-Client and Client-to-Server
		// communication.
		Tyrannonetwork.registerPackets();

		event.enqueueWork(() -> {
			// Adds to the Stripping Map
			TyrannoLogBlock.addStripping();

			// Adds new Bee Hives to the BE's valid blocks
			PoiType.BEEHIVE.matchingStates = Sets.newHashSet(PoiType.BEEHIVE.matchingStates);
			Map<BlockState, PoiType> statePointOfInterestMap = ObfuscationReflectionHelper.getPrivateValue(PoiType.class, null, "f_27323_");
			if (statePointOfInterestMap != null) {
				for (Block block : collectBlocks(TyrannoBeehiveBlock.class)) {
					block.getStateDefinition().getPossibleStates().forEach(state -> {
						statePointOfInterestMap.put(state, PoiType.BEEHIVE);
						PoiType.BEEHIVE.matchingStates.add(state);
					});
				}
			}

			// Adds new Signs to the BE's valid blocks
			ImmutableSet.Builder<Block> signBlocks = ImmutableSet.builder();
			signBlocks.addAll(BlockEntityType.SIGN.validBlocks);
			TyrannoSignManager.forEachSignBlock(signBlocks::add);
			BlockEntityType.SIGN.validBlocks = signBlocks.build();
		});
	}

	// Sets up things on the client
	private void clientSetup(FMLClientSetupEvent event) {
		// Loads the code for giving Tyrannotitan Members a cape
		var clientForgeBus = MinecraftForge.EVENT_BUS;
		clientForgeBus.register(new Capes());
	}

	// Adds TyrannobookLoader to the resource loader
	private void listenersSetup(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(new TyrannobookLoader());
	}

	// Initialises Tyrannimation
	private static void initTyrannimation() {
		if (!hasInitializedTyrannimation) {
			DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ResourceListener::registerReloadListener);
			Tyrannonetwork.initialize();
		}
		hasInitializedTyrannimation = true;
	}
	
	
}
