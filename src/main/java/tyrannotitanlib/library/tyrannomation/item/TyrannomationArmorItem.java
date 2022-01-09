package tyrannotitanlib.library.tyrannomation.item;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import tyrannotitanlib.library.tyrannomation.renderers.TyrannomationArmorRenderer;

public abstract class TyrannomationArmorItem extends ArmorItem {
	public TyrannomationArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IItemRenderProperties() {
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
				return (A) TyrannomationArmorRenderer.getRenderer(TyrannomationArmorItem.this.getClass()).applyEntityStats(_default).applySlot(armorSlot).setCurrentItem(entityLiving, itemStack, armorSlot);
			}
		});
	}

	@Nullable
	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		Class<? extends ArmorItem> clazz = this.getClass();
		TyrannomationArmorRenderer renderer = TyrannomationArmorRenderer.getRenderer(clazz);
		return renderer.getTextureLocation((ArmorItem) stack.getItem()).toString();
	}
}
