package tyrannotitanlib.library.base.util;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

/*
 * This makes it easy to make a item material.
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

public class TyrannoItemTeir implements IItemTier
{
	private final int harvestLevel;
	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int enchantability;
	private final LazyValue<Ingredient> repairItem;
	
	public TyrannoItemTeir(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairItem) 
	{
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairItem = new LazyValue<>(repairItem);
	}

	@Override
	public int getUses() 
	{
		return this.maxUses;
	}

	@Override
	public float getSpeed() 
	{
		return this.efficiency;
	}

	@Override
	public float getAttackDamageBonus() 
	{
		return this.attackDamage;
	}

	@Override
	public int getLevel() 
	{
		return this.harvestLevel;
	}

	@Override
	public int getEnchantmentValue() 
	{
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() 
	{
		return this.repairItem.get();
	}
	
}
