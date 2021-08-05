package tyrannotitanlib.library.base.util.registry;

import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoBiomeRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoBlockRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoEffectRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoEnchantmentRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoEntityRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoFluidRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoItemRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoParticleTypeRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoPointOfInterestTypeRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoPotionRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoProfessionRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoRecipeSerialiserRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoSoundEventRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoTileEntityRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoWorldTypeRegister;

public class TyrannoRegistry 
{
	private final Map<IForgeRegistry<?>, ITyrannoRegistry<?>> helpers = Maps.newHashMap();
	protected final String modid;
	
	public TyrannoRegistry(String modid)
	{
		this.modid = modid;
		this.putDefaultHelpers();
	}
	
	public static TyrannoRegistry create(String modid, Consumer<TyrannoRegistry> consumer)
	{
		TyrannoRegistry helper = new TyrannoRegistry(modid);
		consumer.accept(helper);
		return helper;
	}
	
	public String getModId()
	{
		return this.modid;
	}
	
	public <K extends IForgeRegistryEntry<K>> void putHelper(IForgeRegistry<K> registry, ITyrannoRegistry<K> helper)
	{
		this.helpers.put(registry, helper);
	}
	
	protected void putDefaultHelpers()
	{
		this.putHelper(ForgeRegistries.PARTICLE_TYPES, new TyrannoParticleTypeRegister(this));
		this.putHelper(ForgeRegistries.POTIONS, new TyrannoEffectRegister(this));
		this.putHelper(ForgeRegistries.SOUND_EVENTS, new TyrannoSoundEventRegister(this));
		this.putHelper(ForgeRegistries.POTION_TYPES, new TyrannoPotionRegister(this));
		this.putHelper(ForgeRegistries.ENCHANTMENTS, new TyrannoEnchantmentRegister(this));
		this.putHelper(ForgeRegistries.RECIPE_SERIALIZERS, new TyrannoRecipeSerialiserRegister(this));
		this.putHelper(ForgeRegistries.ITEMS, new TyrannoItemRegister(this));
		this.putHelper(ForgeRegistries.TILE_ENTITIES, new TyrannoTileEntityRegister(this));
		this.putHelper(ForgeRegistries.FLUIDS, new TyrannoFluidRegister(this));
		this.putHelper(ForgeRegistries.BLOCKS, new TyrannoBlockRegister(this));
		this.putHelper(ForgeRegistries.POI_TYPES, new TyrannoPointOfInterestTypeRegister(this));
		this.putHelper(ForgeRegistries.PROFESSIONS, new TyrannoProfessionRegister(this));
		this.putHelper(ForgeRegistries.ENTITIES, new TyrannoEntityRegister(this));
		this.putHelper(ForgeRegistries.BIOMES, new TyrannoBiomeRegister(this));
		this.putHelper(ForgeRegistries.WORLD_TYPES, new TyrannoWorldTypeRegister(this));
	}
	
	@Nonnull
	public <T extends IForgeRegistryEntry<T>, R extends ITyrannoRegistry<T>> R getHelper(IForgeRegistry<T> registry)
	{
		R helper = (R) this.helpers.get(registry);
		if(helper == null)
		{
			throw new NullPointerException();
		}
		return helper;
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<ParticleType<?>>> T particleTypesHelper()
	{
		return this.getHelper(ForgeRegistries.PARTICLE_TYPES);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<Effect>> T effectHelper()
	{
		return this.getHelper(ForgeRegistries.POTIONS);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<SoundEvent>> T soundEventHelper()
	{
		return this.getHelper(ForgeRegistries.SOUND_EVENTS);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<Potion>> T potionHelper()
	{
		return this.getHelper(ForgeRegistries.POTION_TYPES);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<Enchantment>> T enchantmentHelper()
	{
		return this.getHelper(ForgeRegistries.ENCHANTMENTS);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<IRecipeSerializer<?>>> T recipeSerialiserHelper()
	{
		return this.getHelper(ForgeRegistries.RECIPE_SERIALIZERS);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<Item>> T itemHelper()
	{
		return this.getHelper(ForgeRegistries.ITEMS);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<TileEntityType<?>>> T tileEntityHelper()
	{
		return this.getHelper(ForgeRegistries.TILE_ENTITIES);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<Fluid>> T fluidHelper()
	{
		return this.getHelper(ForgeRegistries.FLUIDS);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<Block>> T blockHelper()
	{
		return this.getHelper(ForgeRegistries.BLOCKS);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<PointOfInterestType>> T pointOfInterestTypeHelper()
	{
		return this.getHelper(ForgeRegistries.POI_TYPES);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<VillagerProfession>> T professionHelper()
	{
		return this.getHelper(ForgeRegistries.PROFESSIONS);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<EntityType<?>>> T entityHelper()
	{
		return this.getHelper(ForgeRegistries.ENTITIES);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<Biome>> T biomeHelper()
	{
		return this.getHelper(ForgeRegistries.BIOMES);
	}
	
	@Nonnull
	public <T extends AbstractTyrannoRegistry<ForgeWorldType>> T worldTypeHelper()
	{
		return this.getHelper(ForgeRegistries.WORLD_TYPES);
	}
	
	public void register(IEventBus bus)
	{
		this.helpers.values().forEach(helper ->
		{
			helper.register(bus);	
		});
	}
}
