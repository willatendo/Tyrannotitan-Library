package tyrannotitanlib.library.tyrannomation.resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.eliotlash.molang.MolangParser;

import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener.IStage;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import tyrannotitanlib.library.TyrannotitanMod;
import tyrannotitanlib.library.tyrannomation.file.AnimationFile;
import tyrannotitanlib.library.tyrannomation.file.AnimationFileLoader;
import tyrannotitanlib.library.tyrannomation.file.TyrannomationModelLoader;
import tyrannotitanlib.library.tyrannomation.molang.MolangRegistrar;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;

public class TyrannomationCache 
{
	private static TyrannomationCache INSTANCE;

	private final AnimationFileLoader animationLoader;
	private final TyrannomationModelLoader modelLoader;

	public final MolangParser parser = new MolangParser();

	public Map<ResourceLocation, AnimationFile> getAnimations() 
	{
		if(!TyrannotitanMod.hasInitialized) 
		{
			throw new RuntimeException("TyrannotitanLib was never initialized! Please read the documentation!");
		}
		return animations;
	}

	public Map<ResourceLocation, TyrannomationModel> getTyrannoModels() 
	{
		if(!TyrannotitanMod.hasInitialized) 
		{
			throw new RuntimeException("TyrannotitanLib was never initialized! Please read the documentation!");
		}
		return geoModels;
	}

	private Map<ResourceLocation, AnimationFile> animations = Collections.emptyMap();
	private Map<ResourceLocation, TyrannomationModel> geoModels = Collections.emptyMap();

	protected TyrannomationCache() 
	{
		this.animationLoader = new AnimationFileLoader();
		this.modelLoader = new TyrannomationModelLoader();
		MolangRegistrar.registerVars(parser);
	}

	public static TyrannomationCache getInstance() 
	{
		if(INSTANCE == null) 
		{
			INSTANCE = new TyrannomationCache();
			return INSTANCE;
		}
		return INSTANCE;
	}

	public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) 
	{
		Map<ResourceLocation, AnimationFile> animations = new HashMap<>();
		Map<ResourceLocation, TyrannomationModel> geoModels = new HashMap<>();
		return CompletableFuture.allOf(loadResources(backgroundExecutor, resourceManager, "animations", animation -> animationLoader.loadAllAnimations(parser, animation, resourceManager), animations::put), loadResources(backgroundExecutor, resourceManager, "geo", resource -> modelLoader.loadModel(resourceManager, resource), geoModels::put)).thenCompose(stage::wait).thenAcceptAsync(empty -> 
		{
			this.animations = animations;
			this.geoModels = geoModels;
		}, gameExecutor);
	}

	private static <T> CompletableFuture<Void> loadResources(Executor executor, IResourceManager resourceManager, String type, Function<ResourceLocation, T> loader, BiConsumer<ResourceLocation, T> map) 
	{
		return CompletableFuture.supplyAsync(() -> resourceManager.listResources(type, fileName -> fileName.endsWith(".json")), executor).thenApplyAsync(resources -> 
		{
			Map<ResourceLocation, CompletableFuture<T>> tasks = new HashMap<>();
			
			for(ResourceLocation resource : resources) 
			{
				CompletableFuture<T> existing = tasks.put(resource, CompletableFuture.supplyAsync(() -> loader.apply(resource), executor));
				
				if(existing != null) 
				{
					System.err.println("Duplicate resource for " + resource);
					existing.cancel(false);
				}
			}
			
			return tasks;
		}, executor).thenAcceptAsync(tasks -> 
		{
			for(Entry<ResourceLocation, CompletableFuture<T>> entry : tasks.entrySet()) 
			{
				map.accept(entry.getKey(), entry.getValue().join());
			}
		}, executor);
	}
}
