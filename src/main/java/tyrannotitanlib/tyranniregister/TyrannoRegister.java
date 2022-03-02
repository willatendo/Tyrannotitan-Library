package tyrannotitanlib.tyranniregister;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ArrayListMultimap;
import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.world.ForgeWorldPreset;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import tyrannotitanlib.library.recipe.TyrannoRecipeType;
import tyrannotitanlib.tyranniworld.TyrannoBiome;

public class TyrannoRegister {
	private static final Map<String, ModData> modData = new HashMap<>();

	private static ModData getCurrentModData() {
		return getModData(ModLoadingContext.get().getActiveNamespace());
	}

	private static ModData getModData(String modid) {
		ModData data = modData.get(modid);
		if (data == null) {
			data = new ModData();
			modData.put(modid, data);

			FMLJavaModLoadingContext.get().getModEventBus().register(TyrannoRegister.class);
		}

		return data;
	}

	@SubscribeEvent
	public static void onRegistryEvent(RegistryEvent.Register<?> event) {
		getCurrentModData().register(event.getRegistry());
	}

	// Non-Forge
	public static <T extends RecipeType> T registerType(String modId, String id) {
		return (T) Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(modId, id), new TyrannoRecipeType<>());
	}

	public static BannerPattern registerPattern(String id) {
		return BannerPattern.create(id.toUpperCase(Locale.ROOT), id, id, false);
	}

	public static StructurePieceType registerStructurePiece(String modId, String id, StructurePieceType type) {
		return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(modId, id.toLowerCase(Locale.ROOT)), type);
	}

	public static ConfiguredStructureFeature<?, ?> registerConfiguredStructure(String modId, String id, ConfiguredStructureFeature<?, ?> structureFeature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(modId, id), structureFeature);
	}

	public static Codec<RuleSource> registerSurfaceRule(String modId, String id, Codec<RuleSource> codec) {
		return Registry.register(Registry.RULE, new ResourceLocation(modId, id), codec);
	}

	// Forge
	public static Block registerBlock(String id, Block block) {
		register(id, block);
		return block;
	}

	public static Block registerBlock(String id, Block block, CreativeModeTab tab) {
		register(id, block);
		registerItem(id, new BlockItem(block, new Properties().tab(tab)));
		return block;
	}

	public static Fluid registerFluid(String id, Fluid fluid) {
		register(id, fluid);
		return fluid;
	}

	public static Item registerItem(String id, Item item) {
		register(id, item);
		return item;
	}

	public static MobEffect registerMobEffect(String id, MobEffect mobEffect) {
		register(id, mobEffect);
		return mobEffect;
	}

	public static SoundEvent registerSoundEvent(String id, SoundEvent soundEvent) {
		register(id, soundEvent);
		return soundEvent;
	}

	public static Potion registerPotion(String id, Potion potion) {
		register(id, potion);
		return potion;
	}

	public static Enchantment registerEnchantment(String id, Enchantment enchantment) {
		register(id, enchantment);
		return enchantment;
	}

	public static <T extends Entity> EntityType<T> registerEntity(String id, EntityType<T> entityType) {
		register(id, entityType);
		return entityType;
	}

	public static <T extends Entity> EntityType<T> registerEntity(String id, EntityFactory<T> factory, MobCategory category, float width, float height) {
		EntityType<T> entity = EntityType.Builder.of(factory, category).sized(width, height).build(id);
		return registerEntity(id, entity);
	}

	public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, BlockEntityType<T> blockEntityType) {
		register(id, blockEntityType);
		return blockEntityType;
	}

	public static <T extends ParticleOptions> ParticleType<T> registerParticleType(String id, ParticleType<T> particle) {
		register(id, particle);
		return particle;
	}

	public static ParticleType<SimpleParticleType> registerSimpleParticle(String id, SimpleParticleType particle) {
		return registerParticleType(id, new SimpleParticleType(false));
	}

	public static <T extends AbstractContainerMenu> MenuType<T> registerMenu(String id, MenuType<T> menuType) {
		register(id, menuType);
		return menuType;
	}

	public static Motive registerMotive(String id, Motive motive) {
		register(id, motive);
		return motive;
	}

	public static RecipeSerializer registerRecipeSerializer(String id, RecipeSerializer recipeSerializer) {
		register(id, recipeSerializer);
		return recipeSerializer;
	}

	public static Attribute registerAttribute(String id, Attribute attribute) {
		register(id, attribute);
		return attribute;
	}

	public static <T> StatType<T> registerStatType(String id, StatType<T> statType) {
		register(id, statType);
		return statType;
	}

	public static VillagerProfession registerVillagerProfession(String id, VillagerProfession villagerProfession) {
		register(id, villagerProfession);
		return villagerProfession;
	}

	public static PoiType registerPOIType(String id, PoiType poiType) {
		register(id, poiType);
		return poiType;
	}

	public static <T> MemoryModuleType<T> registerMemoryModuleType(String id, MemoryModuleType<T> memoryModuleType) {
		register(id, memoryModuleType);
		return memoryModuleType;
	}

	public static <T extends Sensor<?>> SensorType<T> registerSensorType(String id, SensorType<T> sensorType) {
		register(id, sensorType);
		return sensorType;
	}

	public static Schedule registerSchedule(String id, Schedule schedule) {
		register(id, schedule);
		return schedule;
	}

	public static Activity registerActivity(String id, Activity activity) {
		register(id, activity);
		return activity;
	}

	public static <T extends CarverConfiguration> WorldCarver<T> registerWorldCarver(String id, WorldCarver worldCarver) {
		register(id, worldCarver);
		return worldCarver;
	}

	public static <T extends FeatureConfiguration> Feature<T> registerFeature(String id, Feature<T> feature) {
		register(id, feature);
		return feature;
	}

	public static ChunkStatus registerChunkStatus(String id, ChunkStatus chunkStatus) {
		register(id, chunkStatus);
		return chunkStatus;
	}

	public static <T extends FeatureConfiguration> StructureFeature<T> registerStructureFeature(String id, StructureFeature<T> structureFeature) {
		register(id, structureFeature);
		return structureFeature;
	}

	public static <T extends BlockStateProvider> BlockStateProviderType<T> registerBlockStateProviderType(String id, BlockStateProviderType<T> blockStateProviderType) {
		register(id, blockStateProviderType);
		return blockStateProviderType;
	}

	public static <T extends FoliagePlacer> FoliagePlacerType<T> registerFoliagePlacerType(String id, FoliagePlacerType<T> foliagePlacerType) {
		register(id, foliagePlacerType);
		return foliagePlacerType;
	}

	public static <T extends TreeDecorator> TreeDecoratorType<T> registerTreeDecoratorType(String id, TreeDecoratorType<T> treeDecoratorType) {
		register(id, treeDecoratorType);
		return treeDecoratorType;
	}

	public static Biome registerBiome(String id, Biome biome) {
		register(id, biome);
		return biome;
	}

	public static Biome registerBiome(TyrannoBiome biome) {
		var realBiome = biome.getBiome();
		register(biome.name().getPath(), realBiome);
		return realBiome;
	}

	public static DataSerializerEntry registerDataSerializerEntry(String id, DataSerializerEntry dataSerializerEntry) {
		register(id, dataSerializerEntry);
		return dataSerializerEntry;
	}

	public static <T extends IGlobalLootModifier> GlobalLootModifierSerializer<T> registerGlobalLootModifierSerializer(String id, GlobalLootModifierSerializer<T> globalLootModifierSerializer) {
		register(id, globalLootModifierSerializer);
		return globalLootModifierSerializer;
	}

	public static ForgeWorldPreset registerWorldPreset(String id, ForgeWorldPreset worldPreset) {
		register(id, worldPreset);
		return worldPreset;
	}

	public static <T extends IForgeRegistryEntry<T>> void register(String id, IForgeRegistryEntry<T> entry) {
		if (entry == null) {
			throw new IllegalArgumentException("Can't register null object.");
		}

		entry.setRegistryName(GameData.checkPrefix(id, false));
		getCurrentModData().defers.put(entry.getRegistryType(), () -> entry);
	}

	private static class ModData {
		private ArrayListMultimap<Class<?>, Supplier<IForgeRegistryEntry<?>>> defers = ArrayListMultimap.create();

		private void register(IForgeRegistry registry) {
			Class<?> type = registry.getRegistrySuperType();

			if (defers.containsKey(type)) {
				Collection<Supplier<IForgeRegistryEntry<?>>> ourEntries = defers.get(type);
				for (Supplier<IForgeRegistryEntry<?>> supplier : ourEntries) {
					IForgeRegistryEntry<?> entry = supplier.get();
					registry.register(entry);
				}

				defers.removeAll(type);
			}
		}
	}
}
