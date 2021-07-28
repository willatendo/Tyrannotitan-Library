package tyrannotitanlib.library.base.util.registry.registries;

import java.util.function.Supplier;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoTileEntityRegister extends AbstractTyrannoRegistry<TileEntityType<?>>
{
	public TyrannoTileEntityRegister(TyrannoRegistry registry, DeferredRegister<TileEntityType<?>> deferredRegister) 
	{
		super(registry, deferredRegister);
	} 
	
	public TyrannoTileEntityRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, registry.getModId()));
	}
	
	public <T extends TileEntity> RegistryObject<TileEntityType<T>> build(String name, Supplier<? extends T> tileEntity, Supplier<Block[]> block) 
	{
		return this.deferredRegister.register(name, () -> new TileEntityType<>(tileEntity, Sets.newHashSet(block.get()), null));
	}

	public <T extends TileEntity> RegistryObject<TileEntityType<T>> build(String name, Supplier<? extends T> tileEntity, Class<? extends Block> blockClass) 
	{
		return this.deferredRegister.register(name, () -> new TileEntityType<>(tileEntity, Sets.newHashSet(collectBlocks(blockClass)), null));
	}
	
	public static Block[] collectBlocks(Class<?> blockClass) 
	{
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
}
