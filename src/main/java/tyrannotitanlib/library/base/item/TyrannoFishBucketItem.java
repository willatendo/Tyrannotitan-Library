package tyrannotitanlib.library.base.item;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.FishBucketItem;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;

import net.minecraft.world.item.Item.Properties;

public class TyrannoFishBucketItem extends FishBucketItem
{
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;
	
	public TyrannoFishBucketItem(NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, Fluid fluid, Properties properties) 
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
