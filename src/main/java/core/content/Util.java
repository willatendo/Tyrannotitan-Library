package core.content;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {
	public static final String TYRANNO_ID = "tyrannotitanlib";
	public static final UtilitiesRegistry TYRANNO_UTILS = new UtilitiesRegistry("tyrannotitanlib");
	public static final Logger LOG = TYRANNO_UTILS.getLogger();

	// Official Mod Utilities
	public static final String LOST_WORLDS_ID = "lostworlds";
	public static final UtilitiesRegistry LOST_WORLDS_UTILS = new UtilitiesRegistry("lostworlds");
	public static final Logger LW_LOG = LOST_WORLDS_UTILS.getLogger();

	public static final String HYLANDA_ID = "hylanda";
	public static final UtilitiesRegistry HYLANDA_UTILS = new UtilitiesRegistry("hylanda");
	public static final Logger HYLANDA_LOG = HYLANDA_UTILS.getLogger();

	public static final String ROSES_ID = "roses";
	public static final UtilitiesRegistry ROSES_UTILS = new UtilitiesRegistry("roses");
	public static final Logger ROSES_LOG = ROSES_UTILS.getLogger();

	// Common Utilities
	@Nullable
	public static BufferedReader getURLContents(@Nonnull String urlString, @Nonnull String backupFileLoc) {
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			InputStream stream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(stream);

			return new BufferedReader(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			return new BufferedReader(new InputStreamReader(Util.class.getClass().getClassLoader().getResourceAsStream(backupFileLoc), StandardCharsets.UTF_8));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void registerJigsaw(MinecraftServer server, ResourceLocation poolLocation, ResourceLocation nbtLocation, int weight) {
		RegistryAccess manager = server.registryAccess();
		Registry<StructureTemplatePool> pools = manager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
		StructureTemplatePool pool = pools.get(poolLocation);

		StructureProcessorList processorList = manager.registryOrThrow(Registry.PROCESSOR_LIST_REGISTRY).getOptional(poolLocation).orElse(ProcessorLists.EMPTY);
		List<StructurePoolElement> elements = pool.templates;

		StructurePoolElement element = StructurePoolElement.legacy(nbtLocation.toString(), processorList).apply(StructureTemplatePool.Projection.RIGID);
		for (int i = 0; i < weight; i++) {
			elements.add(element);
		}

		List<Pair<StructurePoolElement, Integer>> elementCounts = new ArrayList(pool.rawTemplates);

		elements.addAll(pool.templates);
		elementCounts.addAll(pool.rawTemplates);

		pool.templates = elements;
		pool.rawTemplates = elementCounts;
	}

	public static Block[] collectBlocks(Class<?> blockClass) {
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
}
