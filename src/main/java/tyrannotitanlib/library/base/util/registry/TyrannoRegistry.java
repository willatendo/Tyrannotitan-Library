package tyrannotitanlib.library.base.util.registry;

import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoBiomeRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoBlockRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoEntityRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoItemRegister;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoTileEntityRegister;

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
		this.putHelper(ForgeRegistries.ITEMS, new TyrannoItemRegister(this));
		this.putHelper(ForgeRegistries.TILE_ENTITIES, new TyrannoTileEntityRegister(this));
		this.putHelper(ForgeRegistries.BLOCKS, new TyrannoBlockRegister(this));
		this.putHelper(ForgeRegistries.ENTITIES, new TyrannoEntityRegister(this));
		this.putHelper(ForgeRegistries.BIOMES, new TyrannoBiomeRegister(this));
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
	public <T extends AbstractTyrannoRegistry<Block>> T blockHelper()
	{
		return this.getHelper(ForgeRegistries.BLOCKS);
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
	
	public void register(IEventBus bus)
	{
		this.helpers.values().forEach(helper ->
		{
			helper.register(bus);	
		});
	}
}
