package tyrannotitanlib.library.base.item;

import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.FishBucketItem;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;

public class TyrannoFishBucketItem extends FishBucketItem
{
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;
	
	//Use this constructor.
	public TyrannoFishBucketItem(NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, Fluid fluid, Properties properties) 
	{
		super(null, fluid, properties);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
	}

	public TyrannoFishBucketItem(RegistryObject<? extends EntityType<?>> entityTypeSupplier, Fluid fluid, Properties properties) 
	{
		super(null, fluid, properties);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
	}

	@Override
	protected EntityType<?> getFishType() 
	{
		return this.entityTypeSupplier.get();
	}
}
