package tyrannotitanlib.library.utils;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.datafixers.util.Pair;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class TyrannoUtils {
	public static List<String> TYRANNOTITANS = new ArrayList<>();

	public static final Logger LOGGER = LogManager.getLogger(TyrannoUtils.TYRANNO_ID);

	public static final String TYRANNO_ID = "tyrannotitanlib";

	public static ResourceLocation rL(String location) {
		return new ResourceLocation(TYRANNO_ID, location);
	}

	public static TranslatableComponent sTC(String key) {
		return new TranslatableComponent(key);
	}

	public static TranslatableComponent tTC(String type, String key) {
		return new TranslatableComponent(type + "." + TYRANNO_ID + "." + key);
	}

	public static TranslatableComponent cTC(String type, String key, ChatFormatting colour) {
		TranslatableComponent text = tTC(type, key);
		text.withStyle(colour);
		return text;
	}

	public static TranslatableComponent gTC(String type, String key) {
		TranslatableComponent text = tTC(type, key);
		text.withStyle(ChatFormatting.GRAY);
		return text;
	}

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
			return new BufferedReader(new InputStreamReader(TyrannoUtils.class.getClass().getClassLoader().getResourceAsStream(backupFileLoc), StandardCharsets.UTF_8));
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
}
