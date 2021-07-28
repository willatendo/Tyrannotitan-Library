package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoEntityRegister extends AbstractTyrannoRegistry<EntityType<?>> 
{
	public TyrannoEntityRegister(TyrannoRegistry registry, DeferredRegister<EntityType<?>> deferredRegister) 
	{
		super(registry, deferredRegister);
	}

	public TyrannoEntityRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.ENTITIES, registry.getModId()));
	}

	public <T extends Entity> RegistryObject<EntityType<T>> build(String name, EntityType.IFactory<T> entity, EntityClassification entitytype, Class<T> entityClass, float width, float height) 	
	{
		return this.deferredRegister.register(name, () -> EntityType.Builder.of(entity, entitytype).sized(width, height).build(name));
	}
}
