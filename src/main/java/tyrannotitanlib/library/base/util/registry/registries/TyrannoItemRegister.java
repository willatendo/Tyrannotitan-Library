package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoItemRegister extends AbstractTyrannoRegistry<Item>
{
	public TyrannoItemRegister(TyrannoRegistry registry, DeferredRegister<Item> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoItemRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.ITEMS, registry.getModId()));
	}
	
	public <I extends Item> RegistryObject<I> build(String id, I item)
	{
		return this.deferredRegister.register(id, () -> item);
	}
}
