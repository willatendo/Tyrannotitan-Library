package tyrannimation.renderers;

import java.awt.Color;
import java.util.Collections;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import tyrannimation.animation.render.built.TyrannomationModel;
import tyrannimation.core.IAnimated;
import tyrannimation.core.IAnimatedModel;
import tyrannimation.core.controller.TyrannomationController;
import tyrannimation.core.event.predicate.TyrannomationEvent;
import tyrannimation.model.AnimatedModel;
import tyrannimation.model.provider.AnimatedModelProvider;
import tyrannimation.model.provider.data.EntityModelData;
import tyrannimation.util.TyrannomationUtils;

public class TyrannomationProjectilesRenderer<T extends Entity & IAnimated> extends EntityRenderer<T> implements IAnimatedRenderer<T> {
	static {
		TyrannomationController.addModelFetcher((IAnimated object) -> {
			if (object instanceof Entity) {
				return (IAnimatedModel<?>) TyrannomationUtils.getTyrannoModelForEntity((Entity) object);
			}
			return null;
		});
	}

	private final AnimatedModel<T> modelProvider;

	protected TyrannomationProjectilesRenderer(EntityRendererProvider.Context renderManager, AnimatedModel<T> modelProvider) {
		super(renderManager);
		this.modelProvider = modelProvider;
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixstack, MultiBufferSource bufferIn, int packedLightIn) {
		TyrannomationModel model = modelProvider.getModel(modelProvider.getModelLocation(entity));
		matrixstack.pushPose();
		matrixstack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
		matrixstack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
		RenderSystem.setShaderTexture(0, getTextureLocation(entity));
		Color renderColor = getRenderColor(entity, partialTicks, matrixstack, bufferIn, null, packedLightIn);
		RenderType renderType = getRenderType(entity, partialTicks, matrixstack, bufferIn, null, packedLightIn, getTextureLocation(entity));
		render(model, entity, partialTicks, renderType, matrixstack, bufferIn, null, packedLightIn, getPackedOverlay(entity, 0), (float) renderColor.getRed() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getAlpha() / 255);

		float lastLimbDistance = 0.0F;
		float limbSwing = 0.0F;
		EntityModelData entityModelData = new EntityModelData();
		TyrannomationEvent<T> predicate = new TyrannomationEvent<T>(entity, limbSwing, lastLimbDistance, partialTicks, !(lastLimbDistance > -0.15F && lastLimbDistance < 0.15F), Collections.singletonList(entityModelData));
		if (modelProvider instanceof IAnimatedModel) {
			((IAnimatedModel<T>) modelProvider).setLivingAnimations(entity, this.getUniqueID(entity), predicate);
		}
		matrixstack.popPose();
		super.render(entity, entityYaw, partialTicks, matrixstack, bufferIn, packedLightIn);
	}

	public static int getPackedOverlay(Entity livingEntityIn, float uIn) {
		return OverlayTexture.pack(OverlayTexture.u(uIn), OverlayTexture.v(false));
	}

	@Override
	public AnimatedModelProvider<T> getAnimatedModelProvider() {
		return this.modelProvider;
	}

	@Override
	public ResourceLocation getTextureLocation(T instance) {
		return this.modelProvider.getTextureLocation(instance);
	}

}