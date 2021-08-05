package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.potion.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoPotionRegister extends AbstractTyrannoRegistry<Potion>
{
	public TyrannoPotionRegister(TyrannoRegistry registry, DeferredRegister<Potion> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoPotionRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.POTION_TYPES, registry.getModId()));
	}
	
	public Potion build(String id, Potion potion)
	{
		this.deferredRegister.register(id, () -> potion);
		return potion;
	}
}
