package tyrannotitanlib.library.base.item;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public record TyrannoArmourTeir(ResourceLocation name, int maxDamage, int[] damageReductionArray, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairItem) implements IArmorMaterial
{
	private static final int[] MAX_SLOT_DAMAGE = new int[]{13, 15, 16, 11};

	@Override
	public int getDurabilityForSlot(EquipmentSlotType type) 
	{
		return MAX_SLOT_DAMAGE[type.getIndex()] * this.maxDamage;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlotType type) 
	{
		return this.damageReductionArray[type.getIndex()];
	}

	@Override
	public int getEnchantmentValue() 
	{
		return this.enchantability;
	}

	@Override
	public SoundEvent getEquipSound() 
	{
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() 
	{
		return this.repairItem.get();
	}

	@Override
	public String getName() 
	{
		return this.name.toString();
	}

	@Override
	public float getToughness() 
	{
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() 
	{
		return this.knockbackResistance;
	}
	
}
