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
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class TyrannoSpawnEggItem extends SpawnEggItem
{
	protected static final List<TyrannoSpawnEggItem> UNADDED_EGGS = new ArrayList<TyrannoSpawnEggItem>();
	private final Lazy<? extends EntityType<?>> entityTypeSupplier;

	public TyrannoSpawnEggItem(NonNullSupplier<? extends EntityType<?>> entityTypeSupplier, int primaryColour, int secondaryColour, Properties properties) 
	{
		super(null, primaryColour, secondaryColour, properties.tab(ItemGroup.TAB_MISC));
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_EGGS.add(this);
	}

	public TyrannoSpawnEggItem(RegistryObject<? extends EntityType<?>> entityTypeSupplier, int primaryColour, int secondaryColour, Properties properties) 
	{
		super(null, primaryColour, secondaryColour, properties.tab(ItemGroup.TAB_MISC));
		this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
		UNADDED_EGGS.add(this);
	}

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
