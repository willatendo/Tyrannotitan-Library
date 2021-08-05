package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoEnchantmentRegister extends AbstractTyrannoRegistry<Enchantment>
{
	public TyrannoEnchantmentRegister(TyrannoRegistry registry, DeferredRegister<Enchantment> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoEnchantmentRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, registry.getModId()));
	}
	
	public Enchantment build(String id, Enchantment enchantment)
	{
		this.deferredRegister.register(id, () -> enchantment);
		return enchantment;
	}
}
