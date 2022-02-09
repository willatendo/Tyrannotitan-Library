package tyrannotitanlib.tyrannimation.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationModel;
import tyrannotitanlib.tyrannimation.core.IAnimated;
import tyrannotitanlib.tyrannimation.model.provider.AnimatedModelProvider;
import tyrannotitanlib.tyrannimation.util.AnimationUtilities;

public abstract class AnimatedLayerRenderer<T extends Entity & IAnimated> {
	private final IAnimatedRenderer<T> entityRenderer;

	public AnimatedLayerRenderer(IAnimatedRenderer<T> entityRendererIn) {
		this.entityRenderer = entityRendererIn;
	}

	protected static <T extends LivingEntity & IAnimated> void renderCopyModel(AnimatedModelProvider<T> modelProviderIn, ResourceLocation textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float partialTicks, float red, float green, float blue) {
		if (!entityIn.isInvisible()) {
			renderModel(modelProviderIn, textureLocationIn, matrixStackIn, bufferIn, packedLightIn, entityIn, partialTicks, red, green, blue);
		}
	}

	protected static <T extends LivingEntity & IAnimated> void renderModel(AnimatedModelProvider<T> modelProviderIn, ResourceLocation textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float partialTicks, float red, float green, float blue) {
		AnimationModel model = modelProviderIn.getModel(modelProviderIn.getModelLocation(entityIn));
		IAnimatedRenderer<T> renderer = (IAnimatedRenderer<T>) AnimationUtilities.getRenderer(entityIn);
		RenderType renderType = getRenderType(textureLocationIn);
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(renderType);
		renderer.render(model, entityIn, partialTicks, renderType, matrixStackIn, bufferIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(entityIn, 0.0F), red, green, blue, 1.0F);
	}

	public static RenderType getRenderType(ResourceLocation textureLocation) {
		return RenderType.entityCutout(textureLocation);
	}

	public AnimatedModelProvider getEntityModel() {
		return this.entityRenderer.getAnimatedModelProvider();
	}

	protected ResourceLocation getEntityTexture(T entityIn) {
		return this.entityRenderer.getTextureLocation(entityIn);
	}

	public abstract void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch);
}
