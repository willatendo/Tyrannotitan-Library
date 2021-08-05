package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.potion.Effect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoEffectRegister extends AbstractTyrannoRegistry<Effect>
{
	public TyrannoEffectRegister(TyrannoRegistry registry, DeferredRegister<Effect> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoEffectRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.POTIONS, registry.getModId()));
	}
	
	public Effect build(String id, Effect effect)
	{
		this.deferredRegister.register(id, () -> effect);
		return effect;
	}
}
