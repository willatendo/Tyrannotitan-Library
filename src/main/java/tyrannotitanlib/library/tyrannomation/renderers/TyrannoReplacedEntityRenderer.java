package tyrannotitanlib.library.tyrannomation.renderers;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TextFormatting;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatableModel;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.model.TyrannomatedTyrannomationModel;
import tyrannotitanlib.library.tyrannomation.model.provider.data.EntityModelData;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;

public abstract class TyrannoReplacedEntityRenderer<T extends ITyrannomatable> extends EntityRenderer implements ITyrannomationRenderer 
{
	private final TyrannomatedTyrannomationModel<ITyrannomatable> modelProvider;
	private final T animatable;
	protected final List<TyrannomationLayerRenderer> layerRenderers = Lists.newArrayList();
	private ITyrannomatable currentAnimatable;
	private static Map<Class<? extends ITyrannomatable>, TyrannoReplacedEntityRenderer> renderers = new ConcurrentHashMap<>();

	static 
	{
		TyrannomationController.addModelFetcher((ITyrannomatable object) -> 
		{
			TyrannoReplacedEntityRenderer renderer = renderers.get(object.getClass());
			return renderer == null ? null : renderer.getGeoModelProvider();
		});
	}

	protected TyrannoReplacedEntityRenderer(EntityRendererManager renderManager, TyrannomatedTyrannomationModel<ITyrannomatable> modelProvider, T animatable) 
	{
		super(renderManager);
		this.modelProvider = modelProvider;
		this.animatable = animatable;
	}

	public static void registerReplacedEntity(Class<? extends ITyrannomatable> itemClass, TyrannoReplacedEntityRenderer renderer) 
	{
		renderers.put(itemClass, renderer);
	}

	public static TyrannoReplacedEntityRenderer getRenderer(Class<? extends ITyrannomatable> item) 
	{
		return renderers.get(item);
	}

	@Override
	public void render(Entity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) 
	{
		this.render(entityIn, this.animatable, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public void render(Entity entity, ITyrannomatable animatable, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) 
	{
		this.currentAnimatable = animatable;
		LivingEntity entityLiving;
		if(entity instanceof LivingEntity) 
		{
			entityLiving = (LivingEntity) entity;
		} 
		else 
		{
			throw (new RuntimeException("Replaced renderer was not an instanceof LivingEntity"));
		}

		stack.pushPose();
		boolean shouldSit = entity.isPassenger() && (entity.getVehicle() != null && entity.getVehicle().shouldRiderSit());
		EntityModelData entityModelData = new EntityModelData();
		entityModelData.isSitting = shouldSit;
		entityModelData.isChild = entityLiving.isBaby();

		float f = MathHelper.rotLerp(partialTicks, entityLiving.yBodyRotO, entityLiving.yBodyRot);
		float f1 = MathHelper.rotLerp(partialTicks, entityLiving.yHeadRotO,	entityLiving.yHeadRot);
		float f2 = f1 - f;
		if(shouldSit && entity.getVehicle() instanceof LivingEntity) 
		{
			LivingEntity livingentity = (LivingEntity) entity.getVehicle();
			f = MathHelper.rotLerp(partialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
			f2 = f1 - f;
			float f3 = MathHelper.wrapDegrees(f2);
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

			f2 = f1 - f;
		}

		float f6 = MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot);
		if(entity.getPose() == Pose.SLEEPING) 
		{
			Direction direction = entityLiving.getBedOrientation();
			if(direction != null) 
			{
				float f4 = entity.getEyeHeight(Pose.STANDING) - 0.1F;
				stack.translate((double) ((float) (-direction.getStepX()) * f4), 0.0D, (double) ((float) (-direction.getStepZ()) * f4));
			}
		}
		float f7 = this.handleRotationFloat(entityLiving, partialTicks);
		this.applyRotations(entityLiving, stack, f7, f, partialTicks);
		this.preRenderCallback(entityLiving, stack, partialTicks);

		float limbSwingAmount = 0.0F;
		float limbSwing = 0.0F;
		if(!shouldSit && entity.isAlive()) 
		{
			limbSwingAmount = MathHelper.lerp(partialTicks, entityLiving.animationSpeedOld, entityLiving.animationSpeed);
			limbSwing = entityLiving.animationPosition - entityLiving.animationSpeed * (1.0F - partialTicks);
			if(entityLiving.isBaby()) 
			{
				limbSwing *= 3.0F;
			}

			if(limbSwingAmount > 1.0F) 
			{
				limbSwingAmount = 1.0F;
			}
		}

		TyrannomationModel model = modelProvider.getModel(modelProvider.getModelLocation(animatable));
		TyrannomationEvent predicate = new TyrannomationEvent(animatable, limbSwing, limbSwingAmount, partialTicks, !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F), Collections.singletonList(entityModelData));
		if(modelProvider instanceof ITyrannomatableModel) 
		{
			((ITyrannomatableModel) modelProvider).setLivingAnimations(animatable, this.getUniqueID(entity), predicate);
		}

		stack.translate(0, 0.01f, 0);
		Minecraft.getInstance().textureManager.bind(getTextureLocation(entity));
		Color renderColor = getRenderColor(animatable, partialTicks, stack, bufferIn, null, packedLightIn);
		RenderType renderType = getRenderType(entity, partialTicks, stack, bufferIn, null, packedLightIn, getTextureLocation(entity));
		boolean invis = entity.isInvisibleTo(Minecraft.getInstance().player);
		render(model, entity, partialTicks, renderType, stack, bufferIn, null, packedLightIn, getPackedOverlay(entityLiving, this.getOverlayProgress(entityLiving, partialTicks)), (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, invis ? 0.0F : (float) renderColor.getAlpha() / 255);

		if(!entity.isSpectator()) 
		{
			for(TyrannomationLayerRenderer layerRenderer : this.layerRenderers) 
			{
				layerRenderer.render(stack, bufferIn, packedLightIn, entity, limbSwing, limbSwingAmount, partialTicks, f7, f2, f6);
			}
		}
		stack.popPose();
		super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
	}

	protected float getOverlayProgress(LivingEntity livingEntityIn, float partialTicks) 
	{
		return 0.0F;
	}

	protected void preRenderCallback(LivingEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) { }

	@Override
	public ResourceLocation getTextureLocation(Entity entity) 
	{
		return getTextureLocation(currentAnimatable);
	}

	@Override
	public TyrannomatedTyrannomationModel getGeoModelProvider() 
	{
		return this.modelProvider;
	}

	public static int getPackedOverlay(LivingEntity livingEntityIn, float uIn) 
	{
		return OverlayTexture.pack(OverlayTexture.u(uIn), OverlayTexture.v(livingEntityIn.hurtTime > 0 || livingEntityIn.deathTime > 0));
	}

	protected void applyRotations(LivingEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) 
	{
		Pose pose = entityLiving.getPose();
		if(pose != Pose.SLEEPING) 
		{
			matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - rotationYaw));
		}

		if(entityLiving.deathTime > 0) 
		{
			float f = ((float) entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = MathHelper.sqrt(f);
			if(f > 1.0F) 
			{
				f = 1.0F;
			}

			matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * this.getDeathMaxRotation(entityLiving)));
		} 
		else if(entityLiving.isAutoSpinAttack()) 
		{
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F - entityLiving.xRot));
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
		else if(entityLiving.hasCustomName() || entityLiving instanceof PlayerEntity) 
		{
			String s = TextFormatting.stripFormatting(entityLiving.getName().getString());
			if(("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof PlayerEntity) || ((PlayerEntity) entityLiving).isModelPartShown(PlayerModelPart.CAPE))) 
			{
				matrixStackIn.translate(0.0D, (double) (entityLiving.getBbHeight() + 0.1F), 0.0D);
				matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
			}
		}

	}

	protected boolean isVisible(LivingEntity livingEntityIn) 
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

	protected float getDeathMaxRotation(LivingEntity entityLivingBaseIn) 
	{
		return 90.0F;
	}

	@Override
	public boolean shouldShowName(Entity entity) 
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
	
	protected float getSwingProgress(LivingEntity livingBase, float partialTickTime) 
	{
		return livingBase.getAttackAnim(partialTickTime);
	}

	protected float handleRotationFloat(LivingEntity livingBase, float partialTicks) 
	{
		return (float) livingBase.tickCount + partialTicks;
	}

	@Override
	public ResourceLocation getTextureLocation(Object instance) 
	{
		return this.modelProvider.getTextureLocation((ITyrannomatable) instance);
	}

	public final boolean addLayer(TyrannomationLayerRenderer<? extends LivingEntity> layer) 
	{
		return this.layerRenderers.add(layer);
	}
}