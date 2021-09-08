package tyrannotitanlib.library.base.item;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public record TyrannoItemTeir(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairItem) implements IItemTier
{
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
