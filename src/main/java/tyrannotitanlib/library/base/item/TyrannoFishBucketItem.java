package tyrannotitanlib.library.base.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;

public class TyrannoFishBucketItem extends MobBucketItem {
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;

	public TyrannoFishBucketItem(NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, Fluid fluid, Properties properties) {
		super(null, fluid, SoundEvents.BUCKET_EMPTY_FISH, properties);
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
	}

	@Override
	protected EntityType<?> getFishType() {
		return this.entityTypeSupplier.get();
	}
}
