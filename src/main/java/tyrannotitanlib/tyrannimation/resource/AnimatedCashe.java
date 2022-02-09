package tyrannotitanlib.tyrannimation.resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.eliotlash.molang.MolangParser;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import tyrannotitanlib.core.content.TyrannotitanLibrary;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationModel;
import tyrannotitanlib.tyrannimation.file.AnimatedFile;
import tyrannotitanlib.tyrannimation.file.AnimatedFileLoader;
import tyrannotitanlib.tyrannimation.file.AnimatedModelLoader;
import tyrannotitanlib.tyrannimation.molang.MolangRegistrar;

public class AnimatedCashe {
	private static AnimatedCashe INSTANCE;

	private final AnimatedFileLoader animationLoader;
	private final AnimatedModelLoader modelLoader;

	public final MolangParser parser = new MolangParser();

	public Map<ResourceLocation, AnimatedFile> getAnimations() {
		if (!TyrannotitanLibrary.hasInitializedTyrannimation) {
			throw new RuntimeException("Tyrannimation was never initialized!");
		}
		return animations;
	}

	public Map<ResourceLocation, AnimationModel> getTyrannoModels() {
		if (!TyrannotitanLibrary.hasInitializedTyrannimation) {
			throw new RuntimeException("Tyrannimation was never initialized!");
		}
		return geoModels;
	}

	private Map<ResourceLocation, AnimatedFile> animations = Collections.emptyMap();
	private Map<ResourceLocation, AnimationModel> geoModels = Collections.emptyMap();

	protected AnimatedCashe() {
		this.animationLoader = new AnimatedFileLoader();
		this.modelLoader = new AnimatedModelLoader();
		MolangRegistrar.registerVars(parser);
	}

	public static AnimatedCashe getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AnimatedCashe();
			return INSTANCE;
		}
		return INSTANCE;
	}

	public CompletableFuture<Void> reload(PreparationBarrier stage, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
		Map<ResourceLocation, AnimatedFile> animations = new HashMap<>();
		Map<ResourceLocation, AnimationModel> geoModels = new HashMap<>();
		return CompletableFuture.allOf(loadResources(backgroundExecutor, resourceManager, "animations", animation -> animationLoader.loadAllAnimations(parser, animation, resourceManager), animations::put), loadResources(backgroundExecutor, resourceManager, "geo", resource -> modelLoader.loadModel(resourceManager, resource), geoModels::put)).thenCompose(stage::wait).thenAcceptAsync(empty -> {
			this.animations = animations;
			this.geoModels = geoModels;
		}, gameExecutor);
	}

	private static <T> CompletableFuture<Void> loadResources(Executor executor, ResourceManager resourceManager, String type, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) {
		return CompletableFuture.supplyAsync(() -> resourceManager.listResources(type, fileName -> fileName.endsWith(".json")), executor).thenApplyAsync(resources -> {
			Map<ResourceLocation, CompletableFuture<T>> tasks = new HashMap<>();

			for (ResourceLocation resource : resources) {
				CompletableFuture<T> existing = tasks.put(resource, CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));

				if (existing != null) {
					System.err.println("Duplicate resource for " + resource);
					existing.cancel(false);
				}
			}

			return tasks;
		}, executor).thenAcceptAsync(tasks -> {
			for (Entry<ResourceLocation, CompletableFuture<T>> entry : tasks.entrySet()) {
				map.accept(entry.getKey(), entry.getValue().join());
			}
		}, executor);
	}
}
