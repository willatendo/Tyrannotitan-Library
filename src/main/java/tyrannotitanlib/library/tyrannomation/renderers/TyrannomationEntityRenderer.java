package tyrannotitanlib.library.tyrannomation.renderers;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatableModel;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.model.TyrannomatedTyrannomationModel;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.model.provider.data.EntityModelData;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.util.TyrannomationUtils;

public abstract class TyrannomationEntityRenderer<T extends LivingEntity & ITyrannomatable> extends EntityRenderer<T> implements ITyrannomationRenderer<T> 
{
	static 
	{
		TyrannomationController.addModelFetcher((ITyrannomatable object) -> 
		{
			if(object instanceof Entity) 
			{
				return (ITyrannomatableModel<?>) TyrannomationUtils.getGeoModelForEntity((Entity) object);
			}
			return null;
		});
	}

	private final TyrannomatedTyrannomationModel<T> modelProvider;
	protected final List<TyrannomationLayerRenderer<T>> layerRenderers = Lists.newArrayList();

	public ItemStack mainHand;
	public ItemStack offHand;
	public ItemStack helmet;
	public ItemStack chestplate;
	public ItemStack leggings;
	public ItemStack boots;
	public MultiBufferSource rtb;
	public ResourceLocation whTexture;

	protected TyrannomationEntityRenderer(EntityRendererProvider.Context renderManager, TyrannomatedTyrannomationModel<T> modelProvider) 
	{
		super(renderManager);
		this.modelProvider = modelProvider;
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) 
	{
		stack.pushPose();
		boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
		EntityModelData entityModelData = new EntityModelData();
		entityModelData.isSitting = shouldSit;
		entityModelData.isChild = entity.isBaby();

		float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
		float f1 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
		float netHeadYaw = f1 - f;
		if(shouldSit && entity.getVehicle() instanceof LivingEntity) 
		{
			LivingEntity livingentity = (LivingEntity) entity.getVehicle();
			f = Mth.rotLerp(partialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
			netHeadYaw = f1 - f;
			float f3 = Mth.wrapDegrees(netHeadYaw);
			if(f3 < -85.0F) 
			{
				f3 = -85.0F;
			}

			if(f3 >= 85.0F) 
			{
				f3 = 85.0F;
			}

			f = f1 - f3;
			if(f3 * f3 > 2500.0F) 
			{
				f += f3 * 0.2F;
			}

			netHeadYaw = f1 - f;
		}

		float headPitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
		if(entity.getPose() == Pose.SLEEPING) 
		{
			Direction direction = entity.getBedOrientation();
			if(direction != null) 
			{
				float f4 = entity.getEyeHeight(Pose.STANDING) - 0.1F;
				stack.translate((double) ((float) (-direction.getStepX()) * f4), 0.0D, (double) ((float) (-direction.getStepZ()) * f4));
			}
		}
		float f7 = this.handleRotationFloat(entity, partialTicks);
		this.applyRotations(entity, stack, f7, f, partialTicks);

		float limbSwingAmount = 0.0F;
		float limbSwing = 0.0F;
		if(!shouldSit && entity.isAlive()) 
		{
			limbSwingAmount = Mth.lerp(partialTicks, entity.animationSpeedOld, entity.animationSpeed);
			limbSwing = entity.animationPosition - entity.animationSpeed * (1.0F - partialTicks);
			if(entity.isBaby()) 
			{
				limbSwing *= 3.0F;
			}

			if(limbSwingAmount > 1.0F) 
			{
				limbSwingAmount = 1.0F;
			}
		}
		entityModelData.headPitch = -headPitch;
		entityModelData.netHeadYaw = -netHeadYaw;

		TyrannomationEvent<T> predicate = new TyrannomationEvent<T>(entity, limbSwing, limbSwingAmount, partialTicks, !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F), Collections.singletonList(entityModelData));
		TyrannomationModel model = modelProvider.getModel(modelProvider.getModelLocation(entity));
		if(modelProvider instanceof ITyrannomatableModel) 
		{
			((ITyrannomatableModel<T>) modelProvider).setLivingAnimations(entity, this.getUniqueID(entity), predicate);
		}

		stack.translate(0, 0.01f, 0);
		Minecraft.getInstance().textureManager.bindForSetup(getTextureLocation(entity));
		Color renderColor = getRenderColor(entity, partialTicks, stack, bufferIn, null, packedLightIn);
		RenderType renderType = getRenderType(entity, partialTicks, stack, bufferIn, null, packedLightIn, getTextureLocation(entity));
		boolean invis = entity.isInvisibleTo(Minecraft.getInstance().player);
		render(model, entity, partialTicks, renderType, stack, bufferIn, null, packedLightIn, getPackedOverlay(entity, 0), (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, invis ? 0.0F : (float) renderColor.getAlpha() / 255);

		if(!entity.isSpectator()) 
		{
			for(TyrannomationLayerRenderer<T> layerRenderer : this.layerRenderers) 
			{
				layerRenderer.render(stack, bufferIn, packedLightIn, entity, limbSwing, limbSwingAmount, partialTicks,
						f7, netHeadYaw, headPitch);
			}
		}
		stack.popPose();
		super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
	}
	
	@Override
	public Integer getUniqueID(T animatable) 
	{
		return animatable.getId();
	}

	@Override
	public void renderEarly(T animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) 
	{
		this.mainHand = animatable.getItemBySlot(EquipmentSlot.MAINHAND);
		this.offHand = animatable.getItemBySlot(EquipmentSlot.OFFHAND);
		this.helmet = animatable.getItemBySlot(EquipmentSlot.HEAD);
		this.chestplate = animatable.getItemBySlot(EquipmentSlot.CHEST);
		this.leggings = animatable.getItemBySlot(EquipmentSlot.LEGS);
		this.boots = animatable.getItemBySlot(EquipmentSlot.FEET);
		this.rtb = renderTypeBuffer;
		this.whTexture = this.getTextureLocation(animatable);
		ITyrannomationRenderer.super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
	}

	@Override
	public TyrannomationModelProvider<T> getGeoModelProvider() 
	{
		return this.modelProvider;
	}

	public static int getPackedOverlay(LivingEntity livingEntityIn, float uIn) 
	{
		return OverlayTexture.pack(OverlayTexture.u(uIn), OverlayTexture.v(livingEntityIn.hurtTime > 0 || livingEntityIn.deathTime > 0));
	}

	protected void applyRotations(T entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) 
	{
		Pose pose = entityLiving.getPose();
		if(pose != Pose.SLEEPING) 
		{
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
		}

		if(entityLiving.deathTime > 0) 
		{
			float f = ((float) entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = Mth.sqrt(f);
			if(f > 1.0F) 
			{
				f = 1.0F;
			}

			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * this.getDeathMaxRotation(entityLiving)));
		}
		else if(entityLiving.isAutoSpinAttack()) 
		{
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F - entityLiving.getXRot()));
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(((float) entityLiving.tickCount + partialTicks) * -75.0F));
		} 
		else if(pose == Pose.SLEEPING) 
		{
			Direction direction = entityLiving.getBedOrientation();
			float f1 = direction != null ? getFacingAngle(direction) : rotationYaw;
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f1));
			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(this.getDeathMaxRotation(entityLiving)));
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270.0F));
		} 
		else if(entityLiving.hasCustomName() || entityLiving instanceof Player) 
		{
			String s = ChatFormatting.stripFormatting(entityLiving.getName().getString());
			if(("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof Player) || ((Player) entityLiving).isModelPartShown(PlayerModelPart.CAPE))) 
			{
				matrixStackIn.translate(0.0D, (double) (entityLiving.getBbHeight() + 0.1F), 0.0D);
				matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
			}
		}
	}

	protected boolean isVisible(T livingEntityIn) 
	{
		return !livingEntityIn.isInvisible();
	}

	private static float getFacingAngle(Direction facingIn) 
	{
		switch (facingIn) 
		{
		case SOUTH:
			return 90.0F;
		case WEST:
			return 0.0F;
		case NORTH:
			return 270.0F;
		case EAST:
			return 180.0F;
		default:
			return 0.0F;
		}
	}

	protected float getDeathMaxRotation(T entityLivingBaseIn) 
	{
		return 90.0F;
	}

	@Override
	public boolean shouldShowName(T entity) 
	{
		double d0 = this.entityRenderDispatcher.distanceToSqr(entity);
		float f = entity.isDiscrete() ? 32.0F : 64.0F;
		if(d0 >= (double) (f * f)) 
		{
			return false;
		} 
		else 
		{
			return entity == this.entityRenderDispatcher.crosshairPickEntity && entity.hasCustomName();
		}
	}
	
	protected float getSwingProgress(T livingBase, float partialTickTime) 
	{
		return livingBase.getAttackAnim(partialTickTime);
	}

	protected float handleRotationFloat(T livingBase, float partialTicks) 
	{
		return (float) livingBase.tickCount + partialTicks;
	}

	@Override
	public ResourceLocation getTextureLocation(T instance) 
	{
		return this.modelProvider.getTextureLocation(instance);
	}

	public final boolean addLayer(TyrannomationLayerRenderer<T> layer) 
	{
		return this.layerRenderers.add(layer);
	}
}
