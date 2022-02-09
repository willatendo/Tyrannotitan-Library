package tyrannotitanlib.tyrannimation.renderers;

import java.awt.Color;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationModel;
import tyrannotitanlib.tyrannimation.core.IAnimated;
import tyrannotitanlib.tyrannimation.core.controller.AnimationController;
import tyrannotitanlib.tyrannimation.core.event.predicate.AnimationEvent;
import tyrannotitanlib.tyrannimation.core.processor.IAnimatedBone;
import tyrannotitanlib.tyrannimation.model.AnimatedModel;
import tyrannotitanlib.tyrannimation.util.AnimationUtilities;

public abstract class AnimatedArmorRenderer<T extends ArmorItem & IAnimated> extends HumanoidModel implements IAnimatedRenderer<T> {
	private static Map<Class<? extends ArmorItem>, AnimatedArmorRenderer> renderers = new ConcurrentHashMap<>();

	static {
		AnimationController.addModelFetcher((IAnimated object) -> {
			if (object instanceof ArmorItem) {
				AnimatedArmorRenderer renderer = renderers.get(object.getClass());
				return renderer == null ? null : renderer.getAnimatedModelProvider();
			}
			return null;
		});
	}

	protected T currentArmorItem;
	protected LivingEntity entityLiving;
	protected ItemStack stack;
	protected EquipmentSlot armorSlot;

	public String headBone = "armorHead";
	public String bodyBone = "armorBody";
	public String rightArmBone = "armorRightArm";
	public String leftArmBone = "armorLeftArm";
	public String rightLegBone = "armorRightLeg";
	public String leftLegBone = "armorLeftLeg";
	public String rightBootBone = "armorRightBoot";
	public String leftBootBone = "armorLeftBoot";

	public static void registerArmorRenderer(Class<? extends ArmorItem> item, AnimatedArmorRenderer renderer) {
		renderers.put(item, renderer);
	}

	public static AnimatedArmorRenderer getRenderer(Class<? extends ArmorItem> item) {
		final AnimatedArmorRenderer renderer = renderers.get(item);
		if (renderer == null) {
			throw new IllegalArgumentException("Renderer not registered for item " + item);
		}
		return renderer;
	}

	private final AnimatedModel<T> modelProvider;

	public AnimatedArmorRenderer(AnimatedModel<T> modelProvider) {
		super(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
		this.modelProvider = modelProvider;
	}

	@Override
	public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.render(0, matrixStackIn, bufferIn, packedLightIn);
	}

	public void render(float partialTicks, PoseStack stack, VertexConsumer bufferIn, int packedLightIn) {
		stack.translate(0.0D, 24 / 16F, 0.0D);
		stack.scale(-1.0F, -1.0F, 1.0F);
		AnimationModel model = modelProvider.getModel(modelProvider.getModelLocation(currentArmorItem));

		AnimationEvent itemEvent = new AnimationEvent(this.currentArmorItem, 0, 0, 0, false, Arrays.asList(this.stack, this.entityLiving, this.armorSlot));
		modelProvider.setLivingAnimations(currentArmorItem, this.getUniqueID(this.currentArmorItem), itemEvent);
		this.fitToBiped();
		stack.pushPose();
		RenderSystem.setShaderTexture(0, getTextureLocation(currentArmorItem));
		Color renderColor = getRenderColor(currentArmorItem, partialTicks, stack, null, bufferIn, packedLightIn);
		RenderType renderType = getRenderType(currentArmorItem, partialTicks, stack, null, bufferIn, packedLightIn, getTextureLocation(currentArmorItem));
		render(model, currentArmorItem, partialTicks, renderType, stack, null, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
		stack.popPose();
		stack.scale(-1.0F, -1.0F, 1.0F);
		stack.translate(0.0D, -24 / 16F, 0.0D);
	}

	protected void fitToBiped() {
		if (!(this.entityLiving instanceof ArmorStand)) {
			if (this.headBone != null) {
				IAnimatedBone headBone = this.modelProvider.getBone(this.headBone);
				AnimationUtilities.copyRotations(this.head, headBone);
				headBone.setPositionX(this.head.x);
				headBone.setPositionY(-this.head.y);
				headBone.setPositionZ(this.head.z);
			}

			if (this.bodyBone != null) {
				IAnimatedBone bodyBone = this.modelProvider.getBone(this.bodyBone);
				AnimationUtilities.copyRotations(this.body, bodyBone);
				bodyBone.setPositionX(this.body.x);
				bodyBone.setPositionY(-this.body.y);
				bodyBone.setPositionZ(this.body.z);
			}

			if (this.rightArmBone != null) {
				IAnimatedBone rightArmBone = this.modelProvider.getBone(this.rightArmBone);
				AnimationUtilities.copyRotations(this.rightArm, rightArmBone);
				rightArmBone.setPositionX(this.rightArm.x + 5);
				rightArmBone.setPositionY(2 - this.rightArm.y);
				rightArmBone.setPositionZ(this.rightArm.z);
			}

			if (this.leftArmBone != null) {
				IAnimatedBone leftArmBone = this.modelProvider.getBone(this.leftArmBone);
				AnimationUtilities.copyRotations(this.leftArm, leftArmBone);
				leftArmBone.setPositionX(this.leftArm.x - 5);
				leftArmBone.setPositionY(2 - this.leftArm.y);
				leftArmBone.setPositionZ(this.leftArm.z);
			}

			if (this.rightLegBone != null) {
				IAnimatedBone rightLegBone = this.modelProvider.getBone(this.rightLegBone);
				AnimationUtilities.copyRotations(this.rightLeg, rightLegBone);
				rightLegBone.setPositionX(this.rightLeg.x + 2);
				rightLegBone.setPositionY(12 - this.rightLeg.y);
				rightLegBone.setPositionZ(this.rightLeg.z);
				if (this.rightBootBone != null) {
					IAnimatedBone rightBootBone = this.modelProvider.getBone(this.rightBootBone);
					AnimationUtilities.copyRotations(this.rightLeg, rightBootBone);
					rightBootBone.setPositionX(this.rightLeg.x + 2);
					rightBootBone.setPositionY(12 - this.rightLeg.y);
					rightBootBone.setPositionZ(this.rightLeg.z);
				}
			}

			if (this.leftLegBone != null) {
				IAnimatedBone leftLegBone = this.modelProvider.getBone(this.leftLegBone);
				AnimationUtilities.copyRotations(this.leftLeg, leftLegBone);
				leftLegBone.setPositionX(this.leftLeg.x - 2);
				leftLegBone.setPositionY(12 - this.leftLeg.y);
				leftLegBone.setPositionZ(this.leftLeg.z);
				if (this.leftBootBone != null) {
					IAnimatedBone leftBootBone = this.modelProvider.getBone(this.leftBootBone);
					AnimationUtilities.copyRotations(this.leftLeg, leftBootBone);
					leftBootBone.setPositionX(this.leftLeg.x - 2);
					leftBootBone.setPositionY(12 - this.leftLeg.y);
					leftBootBone.setPositionZ(this.leftLeg.z);
				}
			}
		}
	}

	@Override
	public AnimatedModel<T> getAnimatedModelProvider() {
		return this.modelProvider;
	}

	@Override
	public ResourceLocation getTextureLocation(T instance) {
		return this.modelProvider.getTextureLocation(instance);
	}

	public AnimatedArmorRenderer setCurrentItem(LivingEntity entityLiving, ItemStack stack, EquipmentSlot armorSlot) {
		this.entityLiving = entityLiving;
		this.stack = stack;
		this.armorSlot = armorSlot;
		this.currentArmorItem = (T) stack.getItem();
		return this;
	}

	public final AnimatedArmorRenderer applyEntityStats(HumanoidModel defaultArmor) {
		this.young = defaultArmor.young;
		this.crouching = defaultArmor.crouching;
		this.riding = defaultArmor.riding;
		this.rightArmPose = defaultArmor.rightArmPose;
		this.leftArmPose = defaultArmor.leftArmPose;
		return this;
	}

	public AnimatedArmorRenderer applySlot(EquipmentSlot slot) {
		modelProvider.getModel(modelProvider.getModelLocation(currentArmorItem));

		IAnimatedBone headBone = this.getAndHideBone(this.headBone);
		IAnimatedBone bodyBone = this.getAndHideBone(this.bodyBone);
		IAnimatedBone rightArmBone = this.getAndHideBone(this.rightArmBone);
		IAnimatedBone leftArmBone = this.getAndHideBone(this.leftArmBone);
		IAnimatedBone rightLegBone = this.getAndHideBone(this.rightLegBone);
		IAnimatedBone leftLegBone = this.getAndHideBone(this.leftLegBone);
		IAnimatedBone rightBootBone = this.getAndHideBone(this.rightBootBone);
		IAnimatedBone leftBootBone = this.getAndHideBone(this.leftBootBone);

		switch (slot) {
		case HEAD:
			if (headBone != null)
				headBone.setHidden(false);
			break;
		case CHEST:
			if (bodyBone != null)
				bodyBone.setHidden(false);
			if (rightArmBone != null)
				rightArmBone.setHidden(false);
			if (leftArmBone != null)
				leftArmBone.setHidden(false);
			break;
		case LEGS:
			if (rightLegBone != null)
				rightLegBone.setHidden(false);
			if (leftLegBone != null)
				leftLegBone.setHidden(false);
			break;
		case FEET:
			if (rightBootBone != null)
				rightBootBone.setHidden(false);
			if (leftBootBone != null)
				leftBootBone.setHidden(false);
			break;
		}
		return this;
	}

	protected IAnimatedBone getAndHideBone(String boneName) {
		if (boneName != null) {
			final IAnimatedBone bone = this.modelProvider.getBone(boneName);
			bone.setHidden(true);
			return bone;
		}
		return null;
	}

	@Override
	public Integer getUniqueID(T animatable) {
		return Objects.hash(this.armorSlot, stack.getItem(), stack.getCount(), stack.hasTag() ? stack.getTag().toString() : 1, this.entityLiving.getUUID().toString());
	}
}
