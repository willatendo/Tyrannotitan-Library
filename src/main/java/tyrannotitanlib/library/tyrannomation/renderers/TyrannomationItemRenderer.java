package tyrannotitanlib.library.tyrannomation.renderers;

import java.awt.Color;
import java.util.Collections;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.model.TyrannomatedTyrannomationModel;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.util.TyrannomationUtil;

public abstract class TyrannomationItemRenderer<T extends Item & ITyrannomatable> extends ItemStackTileEntityRenderer implements ITyrannomationRenderer<T> 
{
	static 
	{
		TyrannomationController.addModelFetcher((ITyrannomatable object) -> 
		{
			if(object instanceof Item) 
			{
				Item item = (Item) object;
				ItemStackTileEntityRenderer renderer = item.getItemStackTileEntityRenderer();
				if(renderer instanceof TyrannomationItemRenderer) 
				{
					return ((TyrannomationItemRenderer<?>) renderer).getGeoModelProvider();
				}
			}
			return null;
		});
	}

	protected TyrannomatedTyrannomationModel<T> modelProvider;
	protected ItemStack currentItemStack;

	public TyrannomationItemRenderer(TyrannomatedTyrannomationModel<T> modelProvider) 
	{
		this.modelProvider = modelProvider;
	}

	public void setModel(TyrannomatedTyrannomationModel<T> model) 
	{
		this.modelProvider = model;
	}

	@Override
	public TyrannomatedTyrannomationModel<T> getGeoModelProvider() 
	{
		return modelProvider;
	}

	@Override
	public void renderByItem(ItemStack itemStack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int p_239207_6_) 
	{
		if(p_239207_2_ == ItemCameraTransforms.TransformType.GUI) 
		{
			matrixStack.pushPose();
			IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
			RenderHelper.setupForFlatItems();
			this.render((T) itemStack.getItem(), matrixStack, bufferIn, combinedLightIn, itemStack);
			irendertypebuffer$impl.endBatch();
			RenderSystem.enableDepthTest();
			RenderHelper.setupFor3DItems();
			matrixStack.popPose();
		} 
		else 
		{
			this.render((T) itemStack.getItem(), matrixStack, bufferIn, combinedLightIn, itemStack);
		}
	}

	public void render(T animatable, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn, ItemStack itemStack) 
	{
		this.currentItemStack = itemStack;
		TyrannomationModel model = modelProvider.getModel(modelProvider.getModelLocation(animatable));
		TyrannomationEvent itemEvent = new TyrannomationEvent(animatable, 0, 0, Minecraft.getInstance().getFrameTime(), false, Collections.singletonList(itemStack));
		modelProvider.setLivingAnimations(animatable, this.getUniqueID(animatable), itemEvent);
		stack.pushPose();
		stack.translate(0, 0.01f, 0);
		stack.translate(0.5, 0.5, 0.5);

		Minecraft.getInstance().textureManager.bind(getTextureLocation(animatable));
		Color renderColor = getRenderColor(animatable, 0, stack, bufferIn, null, packedLightIn);
		RenderType renderType = getRenderType(animatable, 0, stack, bufferIn, null, packedLightIn, getTextureLocation(animatable));
		render(model, animatable, 0, renderType, stack, bufferIn, null, packedLightIn, OverlayTexture.NO_OVERLAY, (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
		stack.popPose();
	}

	protected RenderType getRenderType(T tile, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn, ResourceLocation textureLocation) 
	{
		return RenderType.entityCutoutNoCull(textureLocation);
	}

	protected Color getRenderColor(T tile, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) 
	{
		return new Color(255, 255, 255, 255);
	}

	@Override
	public ResourceLocation getTextureLocation(T instance)
	{
		return this.modelProvider.getTextureLocation(instance);
	}

	@Override
	public Integer getUniqueID(T animatable) 
	{
		return TyrannomationUtil.getIDFromStack(currentItemStack);
	}
}
