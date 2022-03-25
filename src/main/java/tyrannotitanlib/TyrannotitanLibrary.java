package tyrannotitanlib;

import static tyrannotitanlib.core.content.ModUtilities.LOG;
import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_ID;
import static tyrannotitanlib.core.content.ModUtilities.collectBlocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import tyrannotitanlib.core.client.TyrannotitanCapeHandler;
import tyrannotitanlib.core.content.ModUtilities;
import tyrannotitanlib.core.content.init.TyrannoBanners;
import tyrannotitanlib.core.content.init.TyrannoBlockEntities;
import tyrannotitanlib.core.content.init.TyrannoEntities;
import tyrannotitanlib.core.content.init.TyrannoItems;
import tyrannotitanlib.library.TyrannoRegistrate;
import tyrannotitanlib.library.block.TyrannoBeehiveBlock;
import tyrannotitanlib.library.block.TyrannoLogBlock;
import tyrannotitanlib.library.block.TyrannoSignManager;
import tyrannotitanlib.tyrannibook.TyrannobookLoader;
import tyrannotitanlib.tyranninetwork.Tyrannonetwork;

@Mod(TYRANNO_ID)
@EventBusSubscriber(bus = Bus.MOD, modid = TYRANNO_ID)
public class TyrannotitanLibrary {
	public static final NonNullSupplier<TyrannoRegistrate> CENTRAL_REGISTRATE = TyrannoRegistrate.lazy(TYRANNO_ID);
	public static List<String> TYRANNOTITANS = new ArrayList<>();

	public TyrannotitanLibrary() {
		var serverModBus = FMLJavaModLoadingContext.get().getModEventBus();

		serverModBus.addListener(this::commonSetup);
		serverModBus.addListener(this::clientSetup);
		serverModBus.addListener(this::listenersSetup);

		TyrannoBanners.init();
		TyrannoBlockEntities.init();
		TyrannoEntities.init();
		TyrannoItems.init();

		Tyrannonetwork.initialize();
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		var urlContents = ModUtilities.getURLContents("https://raw.githubusercontent.com/Willatendo/Tyrannotitan-Library/master/src/main/resources/assets/tyrannotitanlib/tyrannotitan.txt", "assets/tyrannotitanlib/tyrannotitan.txt");
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

		Tyrannonetwork.registerPackets();

		event.enqueueWork(() -> {
			TyrannoLogBlock.addStripping();

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

			ImmutableSet.Builder<Block> signBlocks = ImmutableSet.builder();
			signBlocks.addAll(BlockEntityType.SIGN.validBlocks);
			TyrannoSignManager.forEachSignBlock(signBlocks::add);
			BlockEntityType.SIGN.validBlocks = signBlocks.build();
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		var clientForgeBus = MinecraftForge.EVENT_BUS;
		clientForgeBus.register(new TyrannotitanCapeHandler());
	}

	private void listenersSetup(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(new TyrannobookLoader());
	}
}
