package tyrannotitanlib.library.tyrannoregister;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ArrayListMultimap;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import tyrannotitanlib.library.base.utils.TyrannoUtils;

public class TyrannoRegister 
{
	private static final Map<String, ModData> modData = new HashMap<>();

	private static ModData getCurrentModData() 
	{
		return getModData(ModLoadingContext.get().getActiveNamespace());
	}

	private static ModData getModData(String modid) 
	{
		ModData data = modData.get(modid);
		if(data == null) 
		{
			data = new ModData();
			modData.put(modid, data);

			FMLJavaModLoadingContext.get().getModEventBus().register(TyrannoRegister.class);
		}

		return data;
	}

	@SubscribeEvent
	public static void onRegistryEvent(RegistryEvent.Register<?> event) 
	{
		getCurrentModData().register(event.getRegistry());
	}

	public static void registerParticle(String id, ParticleType particle)
	{
		register(particle, id);
	}

	public static void registerSound(String id, SoundEvent sound)
	{
		register(sound, id);
	}

	public static void registerEffect(String id, Effect effect)
	{
		register(effect, id);
	}

	public static void registerPotion(String id, Potion potion)
	{
		register(potion, id);
	}	
	
	
	public static void registerEnchantment(String id, Enchantment enchantment)
	{
		register(enchantment, id);
	}
	
 	public static void registerItem(String id, Item item) 
	{
		register(item, id);
	}
	
	public static void registerBlockEntity(String id, TileEntityType blockEntity)
	{
		register(blockEntity, id);
	}
	
	public static void registerContainer(String id, ContainerType container)
	{
		register(container, id);
	}
	
	public static void registerBlock(String id, Block block) 
	{
		register(block, id);
	}
	
	public static void registerBlockPlacer(String id, BlockPlacerType blockPlacer)
	{
		register(blockPlacer, id);
	}
	
	public static void registerPointOfInterest(String id, PointOfInterestType pointOfInterest)
	{
		register(pointOfInterest, id);
	}
	
	public static void registerVillagerProfession(String id, VillagerProfession villagerProfession)
	{
		register(villagerProfession, id);
	}
	
	public static void registerEntity(String id, EntityType type)
	{
		register(type, id);
	}
	
	public static void registerFoliagePlacer(String id, FoliagePlacerType foliagePlacer)
	{
		register(foliagePlacer, id);
	}
	
	public static void registerBiome(String id, Biome biome)
	{
		register(biome, id);
	}
	
	public static void registerStructure(String id, Structure structure)
	{
		register(structure, id);
	}
	
	public static void registerSurfaceBuilder(String id, SurfaceBuilder surfaceBuilder)
	{
		register(surfaceBuilder, id);
	}
	
	public static void registerFeature(String id, Feature feature)
	{
		register(feature, id);
	}
	
	public static void registerWorldCarver(String id, WorldCarver carver)
	{
		register(carver, id);
	}
	
	public static void registerWorldType(String id, ForgeWorldType worldType)
	{
		register(worldType, id);
	}
	
	public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistryEntry<T> entry, String id) 
	{
		if(entry == null)
			throw new IllegalArgumentException("Can't register null object.");

		entry.setRegistryName(GameData.checkPrefix(id, false));
		getCurrentModData().defers.put(entry.getRegistryType(), () -> entry);
	}

	private static class ModData 
	{
		private ArrayListMultimap<Class<?>, Supplier<IForgeRegistryEntry<?>>> defers = ArrayListMultimap.create();

		private void register(IForgeRegistry registry) 
		{	
			Class<?> type = registry.getRegistrySuperType();

			if(defers.containsKey(type)) 
			{
				Collection<Supplier<IForgeRegistryEntry<?>>> ourEntries = defers.get(type);
				for(Supplier<IForgeRegistryEntry<?>> supplier : ourEntries) 
				{
					IForgeRegistryEntry<?> entry = supplier.get();
					registry.register(entry);
					TyrannoUtils.LOGGER.debug("Registering to " + registry.getRegistryName() + " - " + entry.getRegistryName());
				}

				defers.removeAll(type);
			}
		}
	}
}
