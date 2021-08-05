package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoProfessionRegister extends AbstractTyrannoRegistry<VillagerProfession>
{
	public TyrannoProfessionRegister(TyrannoRegistry registry, DeferredRegister<VillagerProfession> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoProfessionRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.PROFESSIONS, registry.getModId()));
	}
	
	public VillagerProfession build(String id, VillagerProfession profession)
	{
		this.deferredRegister.register(id, () -> profession);
		return profession;
	}
}
