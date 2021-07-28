package tyrannotitanlib.library.tyrannomation.renderers.tyranno;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import tyrannotitanlib.library.tyrannomation.core.IAnimatable;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.util.AnimationUtils;

public abstract class TyrannomationLayerRenderer<T extends Entity & IAnimatable> 
{
	private final ITyrannomationRenderer<T> entityRenderer;

	public TyrannomationLayerRenderer(ITyrannomationRenderer<T> entityRendererIn) 
	{
		this.entityRenderer = entityRendererIn;
	}

	protected static <T extends LivingEntity & IAnimatable> void renderCopyModel(TyrannomationModelProvider<T> modelProviderIn, ResourceLocation textureLocationIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entityIn, float partialTicks, float red, float green, float blue) 
	{
		if(!entityIn.isInvisible()) 
		{
			renderModel(modelProviderIn, textureLocationIn, matrixStackIn, bufferIn, packedLightIn, entityIn, partialTicks, red, green, blue);
		}
	}

	protected static <T extends LivingEntity & IAnimatable> void renderModel(TyrannomationModelProvider<T> modelProviderIn, ResourceLocation textureLocationIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entityIn, float partialTicks, float red, float green, float blue) 
	{
		TyrannomationModel model = modelProviderIn.getModel(modelProviderIn.getModelLocation(entityIn));
		ITyrannomationRenderer<T> renderer = (ITyrannomationRenderer<T>) AnimationUtils.getRenderer(entityIn);
		RenderType renderType = getRenderType(textureLocationIn);
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(renderType);
		renderer.render(model, entityIn, partialTicks, renderType, matrixStackIn, bufferIn, ivertexbuilder, packedLightIn, LivingRenderer.getOverlayCoords(entityIn, 0.0F), red, green, blue, 1.0F);
	}

	public static RenderType getRenderType(ResourceLocation textureLocation) 
	{
		return RenderType.entityCutout(textureLocation);
	}

	public TyrannomationModelProvider getEntityModel() 
	{
		return this.entityRenderer.getGeoModelProvider();
	}

	protected ResourceLocation getEntityTexture(T entityIn) 
	{
		return this.entityRenderer.getTextureLocation(entityIn);
	}

	public abstract void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch);
}
