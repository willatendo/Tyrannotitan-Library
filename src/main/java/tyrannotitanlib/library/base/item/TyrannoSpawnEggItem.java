package tyrannotitanlib.library.base.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import tyrannotitanlib.library.base.util.TyrannoUtils;

/*
 * This is a SpawnEggItem class that makes a SpawnEggItem and gives it a ItemGroup, and name without too much trouble.
 * To change the name in another language, use the name that you set.
 * 
 * In your item class, make a register that fills in the ItemGroup.
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

public class TyrannoSpawnEggItem extends SpawnEggItem
{
	protected static final List<TyrannoSpawnEggItem> UNADDED_EGGS = new ArrayList<TyrannoSpawnEggItem>();
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;
	private String itemName;

	//Use this constructor when creating spawn eggs
	public TyrannoSpawnEggItem(final String name, final Properties properties, final NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, final int primaryColour, final int secondaryColour) 
	{
		super(null, primaryColour, secondaryColour, properties.tab(ItemGroup.TAB_MISC));
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		this.itemName = name;
		UNADDED_EGGS.add(this);
	}

	public TyrannoSpawnEggItem(final String name, final Properties properties, final RegistryObject<? extends EntityType<?>> entityTypeSupplier, final int primaryColour, final int secondaryColour) 
	{
		super(null, primaryColour, secondaryColour, properties.tab(ItemGroup.TAB_MISC));
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		this.itemName = name;
		UNADDED_EGGS.add(this);
	}
	
	@Override
	public ITextComponent getName(ItemStack stack) 
	{
		return TyrannoUtils.sTC(itemName);
	}

	//Registers Dispenser Behaviour
	public static void initSpawnEggs() 
	{
		final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "field_195987_b");
		DefaultDispenseItemBehavior dispenseBehaviour = new DefaultDispenseItemBehavior() 
		{
			
			@Override
			protected ItemStack execute(IBlockSource source, ItemStack stack) 
			{
				Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
				EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
				type.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
				stack.shrink(1);
				return stack;
			}
		};

		for (final SpawnEggItem spawnEgg : UNADDED_EGGS) 
		{
			EGGS.put(spawnEgg.getType(null), spawnEgg);
			DispenserBlock.registerBehavior(spawnEgg, dispenseBehaviour);
		}
		UNADDED_EGGS.clear();
	}

	@Override
	public EntityType<?> getType(CompoundNBT nbt) 
	{
		return this.entityTypeSupplier.get();
	}
}
