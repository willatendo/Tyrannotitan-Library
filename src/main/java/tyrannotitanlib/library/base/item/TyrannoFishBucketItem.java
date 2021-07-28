package tyrannotitanlib.library.base.item;

import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;
import tyrannotitanlib.library.base.util.TyrannoUtils;

/*
 * This is a FishBucketItem class that gives a FishBucketItem a supplier, a ItemGroup and a name.
 * To change the name in another language, use the name that you set.
 * 
 * In your item class, make a register that fills in the ItemGroup and Fluid.
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

public class TyrannoFishBucketItem extends FishBucketItem
{
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;
	private String itemName;
	
	//Use this constructor.
	public TyrannoFishBucketItem(final String name, final Properties properties, final ItemGroup group, final NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, final Fluid fluid) 
	{
		super(null, fluid, properties.tab(group));
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		this.itemName = name;
	}

	public TyrannoFishBucketItem(final String name, final Properties properties, final ItemGroup group, final RegistryObject<? extends EntityType<?>> entityTypeSupplier, final Fluid fluid) 
	{
		super(null, fluid, properties.tab(group));
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		this.itemName = name;
	}
	
	@Override
	public ITextComponent getName(ItemStack stack) 
	{
		return TyrannoUtils.sTC(itemName);
	}

	@Override
	protected EntityType<?> getFishType() 
	{
		return this.entityTypeSupplier.get();
	}
}
