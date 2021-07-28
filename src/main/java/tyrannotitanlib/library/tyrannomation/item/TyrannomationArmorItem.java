package tyrannotitanlib.library.tyrannomation.item;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.library.tyrannomation.renderers.tyranno.TyrannomationArmorRenderer;

public abstract class TyrannomationArmorItem extends ArmorItem 
{
	public TyrannomationArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) 
	{
		super(materialIn, slot, builder);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) 
	{
		return (A) TyrannomationArmorRenderer.getRenderer(this.getClass()).applyEntityStats(_default).applySlot(armorSlot).setCurrentItem(entityLiving, itemStack, armorSlot);
	}

	@Nullable
	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) 
	{
		Class<? extends ArmorItem> clazz = this.getClass();
		TyrannomationArmorRenderer renderer = TyrannomationArmorRenderer.getRenderer(clazz);
		return renderer.getTextureLocation((ArmorItem) stack.getItem()).toString();
	}
}
