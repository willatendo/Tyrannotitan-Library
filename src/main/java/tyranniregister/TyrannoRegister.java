package tyranniregister;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ArrayListMultimap;
import com.mojang.serialization.Codec;

import core.library.tyrannotitanlib.recipe.TyrannoRecipeType;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.common.world.ForgeWorldPreset;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

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

	public static <WC extends CarverConfiguration> ConfiguredWorldCarver<WC> registerConfiguredCarver(String modId, String id, ConfiguredWorldCarver<WC> configuredCarver) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_CARVER, new ResourceLocation(modId, id), configuredCarver);
	}
	
	public static Codec<RuleSource> registerSurfaceRule(String modId, String id, Codec<RuleSource> codec) {
		return Registry.register(Registry.RULE, new ResourceLocation(modId, id), codec);
	}

	// Forge
	public static void registerParticle(String id, ParticleType particle) {
		register(particle, id);
	}

	public static void registerSerializer(String id, RecipeSerializer<?> recipe) {
		register(recipe, id);
	}

	public static void registerSound(String id, SoundEvent sound) {
		register(sound, id);
	}

	public static void registerEffect(String id, MobEffect effect) {
		register(effect, id);
	}

	public static void registerPotion(String id, Potion potion) {
		register(potion, id);
	}

	public static void registerEnchantment(String id, Enchantment enchantment) {
		register(enchantment, id);
	}

	public static void registerItem(String id, Item item) {
		register(item, id);
	}

	public static void registerBlockEntity(String id, BlockEntityType blockEntity) {
		register(blockEntity, id);
	}

	public static void registerContainer(String id, MenuType container) {
		register(container, id);
	}

	public static void registerBlock(String id, Block block) {
		register(block, id);
	}

	public static void registerPointOfInterest(String id, PoiType pointOfInterest) {
		register(pointOfInterest, id);
	}

	public static void registerVillagerProfession(String id, VillagerProfession villagerProfession) {
		register(villagerProfession, id);
	}

	public static void registerEntity(String id, EntityType type) {
		register(type, id);
	}

	public static void registerFoliagePlacer(String id, FoliagePlacerType foliagePlacer) {
		register(foliagePlacer, id);
	}

	public static void registerBiome(String id, Biome biome) {
		register(biome, id);
	}

	public static void registerStructure(String id, StructureFeature structure) {
		register(structure, id);
	}	

	public static void registerFeature(String id, Feature feature) {
		register(feature, id);
	}

	public static void registerWorldCarver(String id, WorldCarver carver) {
		register(carver, id);
	}

	public static void registerWorldType(String id, ForgeWorldPreset preset) {
		register(preset, id);
	}

	public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistryEntry<T> entry, String id) {
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
