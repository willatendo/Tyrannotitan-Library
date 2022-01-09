package tyrannotitanlib.library.tyrannomation.renderers;

import java.awt.Color;
import java.util.Collections;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.RenderProperties;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatableModel;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.model.TyrannomatedTyrannomationModel;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.util.TyrannomationUtil;

public abstract class TyrannomationItemRenderer<T extends Item & ITyrannomatable> extends BlockEntityWithoutLevelRenderer implements ITyrannomationRenderer<T> {
	static {
		TyrannomationController.addModelFetcher((ITyrannomatable object) -> {
			if (object instanceof Item) {
				Item item = (Item) object;
				BlockEntityWithoutLevelRenderer renderer = RenderProperties.get(item).getItemStackRenderer();
				if (renderer instanceof TyrannomationItemRenderer) {
					return (ITyrannomatableModel<Object>) ((TyrannomationItemRenderer<?>) renderer).getTyrannoModelProvider();
				}
			}
			return null;
		});
	}

	protected TyrannomatedTyrannomationModel<T> modelProvider;
	protected ItemStack currentItemStack;

	public TyrannomationItemRenderer(TyrannomatedTyrannomationModel<T> modelProvider) {
		this(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels(), modelProvider);
	}

	public TyrannomationItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet, TyrannomatedTyrannomationModel<T> modelProvider) {
		super(dispatcher, modelSet);
		this.modelProvider = modelProvider;
	}

	public void setModel(TyrannomatedTyrannomationModel<T> model) {
		this.modelProvider = model;
	}
	
	@Override
	public TyrannomationModelProvider getTyrannoModelProvider() {
		return this.modelProvider;
	}

	@Override
	public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType p_239207_2_, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
		if (p_239207_2_ == ItemTransforms.TransformType.GUI) {
			matrixStack.pushPose();
			MultiBufferSource.BufferSource irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
			Lighting.setupForFlatItems();
			this.render((T) itemStack.getItem(), matrixStack, bufferIn, combinedLightIn, itemStack);
			irendertypebuffer$impl.endBatch();
			RenderSystem.enableDepthTest();
			Lighting.setupFor3DItems();
			matrixStack.popPose();
		} else {
			this.render((T) itemStack.getItem(), matrixStack, bufferIn, combinedLightIn, itemStack);
		}
	}

	public void render(T animatable, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn, ItemStack itemStack) {
		this.currentItemStack = itemStack;
		TyrannomationModel model = modelProvider.getModel(modelProvider.getModelLocation(animatable));
		TyrannomationEvent itemEvent = new TyrannomationEvent(animatable, 0, 0, Minecraft.getInstance().getFrameTime(), false, Collections.singletonList(itemStack));
		modelProvider.setLivingAnimations(animatable, this.getUniqueID(animatable), itemEvent);
		stack.pushPose();
		stack.translate(0, 0.01f, 0);
		stack.translate(0.5, 0.5, 0.5);

		RenderSystem.setShaderTexture(0, getTextureLocation(animatable));
		Color renderColor = getRenderColor(animatable, 0, stack, bufferIn, null, packedLightIn);
		RenderType renderType = getRenderType(animatable, 0, stack, bufferIn, null, packedLightIn, getTextureLocation(animatable));
		render(model, animatable, 0, renderType, stack, bufferIn, null, packedLightIn, OverlayTexture.NO_OVERLAY, (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
		stack.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(T instance) {
		return this.modelProvider.getTextureLocation(instance);
	}

	@Override
	public Integer getUniqueID(T animatable) {
		return TyrannomationUtil.getIDFromStack(currentItemStack);
	}
}
