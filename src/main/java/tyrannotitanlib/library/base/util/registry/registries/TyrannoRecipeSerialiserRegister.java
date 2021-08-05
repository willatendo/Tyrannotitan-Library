package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoRecipeSerialiserRegister extends AbstractTyrannoRegistry<IRecipeSerializer<?>>
{
	public TyrannoRecipeSerialiserRegister(TyrannoRegistry registry, DeferredRegister<IRecipeSerializer<?>> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoRecipeSerialiserRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, registry.getModId()));
	}
	
	public IRecipeSerializer<?> build(String id, IRecipeSerializer<?> recipeSerialiser)
	{
		this.deferredRegister.register(id, () -> recipeSerialiser);
		return recipeSerialiser;
	}
}
