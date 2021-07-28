package tyrannotitanlib.library.tyrannomation.renderers.tyranno;

import java.awt.Color;
import java.util.Collections;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import tyrannotitanlib.library.tyrannomation.core.IAnimatable;
import tyrannotitanlib.library.tyrannomation.core.IAnimatableModel;
import tyrannotitanlib.library.tyrannomation.core.controller.AnimationController;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.AnimationEvent;
import tyrannotitanlib.library.tyrannomation.model.AnimatedTyrannomationModel;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.model.provider.data.EntityModelData;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.util.AnimationUtils;

public class TyrannomationProjectilesRenderer<T extends Entity & IAnimatable> extends EntityRenderer<T> implements ITyrannomationRenderer<T> 
{
	static 
	{
		AnimationController.addModelFetcher((IAnimatable object) -> 
		{
			if(object instanceof Entity) 
			{
				return (IAnimatableModel<?>) AnimationUtils.getGeoModelForEntity((Entity) object);
			}
			return null;
		});
	}

	private final AnimatedTyrannomationModel<T> modelProvider;

	protected TyrannomationProjectilesRenderer(EntityRendererManager renderManager, AnimatedTyrannomationModel<T> modelProvider) 
	{
		super(renderManager);
		this.modelProvider = modelProvider;
	}

	@Override
	public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) 
	{
		TyrannomationModel model = modelProvider.getModel(modelProvider.getModelLocation(entityIn));
		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.yRotO, entityIn.yRot) - 90.0F));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.xRotO, entityIn.xRot)));
		Minecraft.getInstance().textureManager.bind(getTextureLocation(entityIn));
		Color renderColor = getRenderColor(entityIn, partialTicks, matrixStackIn, bufferIn, null, packedLightIn);
		RenderType renderType = getRenderType(entityIn, partialTicks, matrixStackIn, bufferIn, null, packedLightIn, getTextureLocation(entityIn));
		render(model, entityIn, partialTicks, renderType, matrixStackIn, bufferIn, null, packedLightIn, getPackedOverlay(entityIn, 0), (float) renderColor.getRed() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getAlpha() / 255);

		float lastLimbDistance = 0.0F;
		float limbSwing = 0.0F;
		EntityModelData entityModelData = new EntityModelData();
		AnimationEvent<T> predicate = new AnimationEvent<T>(entityIn, limbSwing, lastLimbDistance, partialTicks, !(lastLimbDistance > -0.15F && lastLimbDistance < 0.15F), Collections.singletonList(entityModelData));
		if(modelProvider instanceof IAnimatableModel) 
		{
			((IAnimatableModel<T>) modelProvider).setLivingAnimations(entityIn, this.getUniqueID(entityIn), predicate);
		}
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	public static int getPackedOverlay(Entity livingEntityIn, float uIn) 
	{
		return OverlayTexture.pack(OverlayTexture.u(uIn), OverlayTexture.v(false));
	}

	@Override
	public TyrannomationModelProvider<T> getGeoModelProvider() 
	{
		return this.modelProvider;
	}

	@Override
	public ResourceLocation getTextureLocation(T instance) 
	{
		return this.modelProvider.getTextureLocation(instance);
	}

}