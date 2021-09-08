package tyrannotitanlib.library.base.util.registry;

import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.registries.ForgeRegistries;

public class SimpleRegisters 
{
	public static ParticleType<BasicParticleType> register(String modid, String id, ParticleType<BasicParticleType> particle)
	{
		particle.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.PARTICLE_TYPES.register(particle);
		return particle;
	}
	
	public static IRecipeSerializer<?> register(String modid, String id, IRecipeSerializer<?> recipe)
	{
		recipe.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.RECIPE_SERIALIZERS.register(recipe);
		return recipe;
	}
	
	public static SoundEvent register(String modid, String id, SoundEvent sound)
	{
		sound.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.SOUND_EVENTS.register(sound);
		return sound;
	}
	
	public static Effect register(String modid, String id, Effect effect)
	{
		effect.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.POTIONS.register(effect);
		return effect;
	}
	
	public static Potion register(String modid, String id, Potion potion)
	{
		potion.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.POTION_TYPES.register(potion);
		return potion;
	}
	
	public static Item register(String modid, String id, Item item)
	{
		item.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.ITEMS.register(item);
		return item;
	}
	
	public static BannerPattern createPattern(String modid, String id) 
	{
		return BannerPattern.create(id.toUpperCase(Locale.ROOT), id, id, false);
	}
	
	public static Block register(String modid, String id, Block block)
	{
		block.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.BLOCKS.register(block);
		return block;
	}
	
	public static BlockPlacerType<?> register(String modid, String id, BlockPlacerType<?> type)
	{
		type.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.BLOCK_PLACER_TYPES.register(type);
		return type;
	}
	
	public static PointOfInterestType register(String modid, String id, PointOfInterestType type)
	{
		type.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.POI_TYPES.register(type);
		return type;
	}
	
	public static VillagerProfession register(String modid, String id, VillagerProfession profession)
	{
		profession.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.PROFESSIONS.register(profession);
		return profession;
	}
	
	public static <T extends Entity> EntityType<T> register(String modid, String id, IFactory<T> entity, EntityClassification classifcation, float width, float height)
	{
		EntityType<T> entityType = EntityType.Builder.of(entity, classifcation).sized(width, height).build(id);
		entityType.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.ENTITIES.register(entityType);
		return entityType;
	}
	
	public static FoliagePlacerType<?> register(String modid, String id, FoliagePlacerType<?> foliagePlacer)
	{
		foliagePlacer.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.FOLIAGE_PLACER_TYPES.register(foliagePlacer);
		return foliagePlacer;
	}
	
	public static Biome register(String modid, String id, Biome biome) 
	{
		biome.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.BIOMES.register(biome);
		return biome;
	}
	
	public static IStructurePieceType register(String modid, String id, IStructurePieceType type) 
	{
		return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(modid, id.toLowerCase(Locale.ROOT)), type);
	}
	
	public static Structure<NoFeatureConfig> register(String modid, String id, Structure<NoFeatureConfig> structure)
	{
		structure.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.STRUCTURE_FEATURES.register(structure);
		return structure;
	}
	
	public static StructureFeature<?, ?> register(String modid, String id, StructureFeature<?, ?> structureFeature)
	{
		return Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(modid, id), structureFeature);
	}
	
	public static SurfaceBuilder<?> register(String modid, String id, SurfaceBuilder<?> surfaceBuilder)
	{
		surfaceBuilder.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.SURFACE_BUILDERS.register(surfaceBuilder);
		return surfaceBuilder;
	}
	
	public static Feature<?> register(String modid, String id, Feature<?> feature)
	{
		feature.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.FEATURES.register(feature);
		return feature;
	}
	
	public static WorldCarver<ProbabilityConfig> register(String modid, String id, WorldCarver<ProbabilityConfig> worldCarver)
	{
		worldCarver.setRegistryName(new ResourceLocation(modid, id));
		ForgeRegistries.WORLD_CARVERS.register(worldCarver);
		return worldCarver;
	}	
	
	public static <WC extends ICarverConfig> ConfiguredCarver<WC> register(String modid, String id, ConfiguredCarver<WC> configuredCarver) 
	{
		return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_CARVER, new ResourceLocation(modid, id), configuredCarver);
	}
}
