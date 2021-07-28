package tyrannotitanlib.library.base.util;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import tyrannotitanlib.library.base.util.interfaces.ITyrannoEdgeBiomeProvider;

public final class TyrannoBiomeUtils 
{
	private static final Map<RegistryKey<Biome>, WeightedNoiseList<RegistryKey<Biome>>> HILL_BIOME_MAP = new HashMap<>();
	private static final Map<OceanType, WeightedNoiseList<RegistryKey<Biome>>> OCEAN_BIOME_MAP = new HashMap<>();
	private static final Map<RegistryKey<Biome>, RegistryKey<Biome>> DEEP_OCEAN_BIOME_MAP = new HashMap<>();
	private static final Set<RegistryKey<Biome>> OCEAN_SET = new HashSet<>();
	private static final Set<RegistryKey<Biome>> SHALLOW_OCEAN_SET = new HashSet<>();
	private static final Map<RegistryKey<Biome>, PrioritizedNoiseList<ITyrannoEdgeBiomeProvider>> EDGE_BIOME_PROVIDER_MAP = new HashMap<>();
	private static final WeightedNoiseList<RegistryKey<Biome>> END_BIOMES = new WeightedNoiseList<>();
	private static final List<Pair<Biome.Attributes, RegistryKey<Biome>>> NETHER_BIOMES = new ArrayList<>();
	private static final Set<ResourceLocation> CUSTOM_END_MUSIC_BIOMES = new HashSet<>();

	static 
	{
		addEndBiome(Biomes.END_MIDLANDS, 15);
		addOceanBiome(OceanType.FROZEN, Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN, 15);
		addOceanBiome(OceanType.COLD, Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN, 15);
		addOceanBiome(OceanType.NORMAL, Biomes.OCEAN, Biomes.DEEP_OCEAN, 15);
		addOceanBiome(OceanType.LUKEWARM, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, 15);
		addOceanBiome(OceanType.WARM, Biomes.WARM_OCEAN, null, 15);
	}
	
	@SafeVarargs
	public static synchronized void addHillBiome(RegistryKey<Biome> biome, Pair<RegistryKey<Biome>, Integer>... hills) 
	{
		WeightedNoiseList<RegistryKey<Biome>> list = HILL_BIOME_MAP.computeIfAbsent(biome, (key) -> new WeightedNoiseList<>());
		for(Pair<RegistryKey<Biome>, Integer> hill : hills) 
		{
			list.add(hill.getFirst(), hill.getSecond());
		}
	}
	
	public static synchronized void addEndBiome(RegistryKey<Biome> key, int weight) 
	{
		END_BIOMES.add(key, weight);
	}

	public static synchronized void markEndBiomeCustomMusic(ResourceLocation biomeName) 
	{
		CUSTOM_END_MUSIC_BIOMES.add(biomeName);
	}

	public static synchronized void addOceanBiome(OceanType type, RegistryKey<Biome> biome, @Nullable RegistryKey<Biome> deep, int weight) 
	{
		OCEAN_BIOME_MAP.computeIfAbsent(type, (key) -> new WeightedNoiseList<>()).add(biome, weight);
		OCEAN_SET.add(biome);
		SHALLOW_OCEAN_SET.add(biome);
		if(deep != null) 
		{
			DEEP_OCEAN_BIOME_MAP.put(biome, deep);
			OCEAN_SET.add(biome);
		}
	}
	
	public static synchronized void addEdgeBiome(RegistryKey<Biome> key, ITyrannoEdgeBiomeProvider provider, Priority priority) 
	{
		EDGE_BIOME_PROVIDER_MAP.computeIfAbsent(key, (k) -> new PrioritizedNoiseList<>()).add(provider, priority);
	}

	public static synchronized void addNetherBiome(Biome.Attributes attributes, RegistryKey<Biome> biome) 
	{
		NETHER_BIOMES.add(Pair.of(attributes, biome));
	}

	@Nullable
	public static RegistryKey<Biome> getHillBiome(RegistryKey<Biome> biome, INoiseRandom random) 
	{
		WeightedNoiseList<RegistryKey<Biome>> list = HILL_BIOME_MAP.get(biome);
		return list != null ? list.get(random) : null;
	}

	public static RegistryKey<Biome> getEndBiome(INoiseRandom random) 
	{
		return END_BIOMES.get(random);
	}
	
	public static boolean shouldPlayCustomEndMusic(ResourceLocation biomeName) 
	{
		return CUSTOM_END_MUSIC_BIOMES.contains(biomeName);
	}

	public static RegistryKey<Biome> getOceanBiome(OceanType type, INoiseRandom random) 
	{
		return OCEAN_BIOME_MAP.getOrDefault(type, new WeightedNoiseList<>()).get(random);
	}

	@Nullable
	public static RegistryKey<Biome> getDeepOceanBiome(RegistryKey<Biome> oceanBiome) 
	{
		return DEEP_OCEAN_BIOME_MAP.get(oceanBiome);
	}

	public static boolean isOceanBiome(RegistryKey<Biome> biome) 
	{
		return OCEAN_SET.contains(biome);
	}

	public static boolean isShallowOceanBiome(RegistryKey<Biome> biome) 
	{
		return SHALLOW_OCEAN_SET.contains(biome);
	}

	@Nullable
	public static RegistryKey<Biome> getEdgeBiome(RegistryKey<Biome> biome, INoiseRandom random, RegistryKey<Biome> northBiome, RegistryKey<Biome> westBiome, RegistryKey<Biome> southBiome, RegistryKey<Biome> eastBiome) 
	{
		PrioritizedNoiseList<ITyrannoEdgeBiomeProvider> edgeBiomeProviderList = EDGE_BIOME_PROVIDER_MAP.get(biome);
		if(edgeBiomeProviderList != null) 
		{
			Pair<ITyrannoEdgeBiomeProvider, RegistryKey<Biome>> pair = edgeBiomeProviderList.getWithCallback(random, edgeBiomeProvider -> edgeBiomeProvider.getEdgeBiome(random, northBiome, westBiome, southBiome, eastBiome));
			if(pair != null) 
			{
				return pair.getSecond();
			}
		}
		return null;
	}
	
	public static List<Pair<Biome.Attributes, Supplier<Biome>>> getModifiedNetherBiomes(List<Pair<Biome.Attributes, Supplier<Biome>>> baseBiomes, Registry<Biome> registry) 
	{
		ImmutableList.Builder<Pair<Biome.Attributes, Supplier<Biome>>> builder = new ImmutableList.Builder<>();
		builder.addAll(baseBiomes);
		NETHER_BIOMES.forEach(registryKeyAttributesPair -> 
		{
			RegistryKey<Biome> biomeRegistryKey = registryKeyAttributesPair.getSecond();
			builder.add(Pair.of(registryKeyAttributesPair.getFirst(), () -> registry.getOrThrow(biomeRegistryKey)));
		});
		return builder.build();
	}

	public static int getId(@Nonnull RegistryKey<Biome> biome)
	{
		return WorldGenRegistries.BIOME.getId(WorldGenRegistries.BIOME.get(biome));
	}

	public static final class WeightedNoiseList<T> 
	{
		private final List<Pair<T, Integer>> entries = Lists.newArrayList();
		private int totalWeight;

		public void add(@Nonnull T value, int weight) 
		{
			this.totalWeight += weight;
			this.entries.add(Pair.of(value, weight));
		}
		
		@Nonnull
		public T get(INoiseRandom random) 
		{
			Iterator<Pair<T, Integer>> iterator = this.entries.iterator();
			T value;
			int randomTotal = random.nextRandom(this.totalWeight);
			do 
			{
				Pair<T, Integer> entry = iterator.next();
				value = entry.getFirst();
				randomTotal -= entry.getSecond();
			} 
			while(randomTotal >= 0);
			return value;
		}

		@Nonnull
		public List<Pair<T, Integer>> getEntries() 
		{
			return this.entries;
		}
	}

	public static final class PrioritizedNoiseList<T> 
	{
		private static final Object DUMMY_CALLBACK = new Object();
		private final EnumMap<Priority, List<T>> priorityListMap = new EnumMap<>(Priority.class);

		public void add(T value, Priority priority) 
		{
			this.priorityListMap.computeIfAbsent(priority, priority1 -> new ArrayList<>()).add(value);
		}

		@Nullable
		public T get(INoiseRandom random) 
		{
			Pair<T, Object> pair = this.getWithCallback(random, o -> DUMMY_CALLBACK);
			return pair != null ? pair.getFirst() : null;
		}

		@Nullable
		public <C> Pair<T, C> getWithCallback(INoiseRandom random, Function<T, C> callbackProcessor) 
		{
			for(List<T> list : this.priorityListMap.values()) 
			{
				int size = list.size();
				if(size > 0) 
				{
					List<T> copy = new ArrayList<>(list);
					while(size > 0) 
					{
						int index = random.nextRandom(size);
						T picked = copy.get(index);
						C callback = callbackProcessor.apply(picked);
						if(callback != null) 
						{
							return Pair.of(picked, callback);
						} 
						else 
						{
							copy.remove(index);
							size--;
						}
					}
				}
			}
			return null;
		}

		@Nonnull
		public EnumMap<Priority, List<T>> getPriorityListMap() 
		{
			return this.priorityListMap;
		}
	}

	public enum OceanType 
	{
		WARM, LUKEWARM, FROZEN, COLD, NORMAL
	}

	public enum Priority 
	{
		HIGHEST, HIGH, NORMAL, LOW, LOWEST
	}
}
