package tyrannotitanlib.library.tyrannomation.renderers;

import java.awt.Color;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.model.TyrannomatedTyrannomationModel;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;

public abstract class TyrannomationBlockmationRenderer<T extends BlockEntity & ITyrannomatable> implements BlockEntityRenderer, ITyrannomationRenderer<T> 
{
	static 
	{
		TyrannomationController.addModelFetcher((ITyrannomatable object) -> 
		{
			if(object instanceof BlockEntity) 
			{
				BlockEntity tile = (BlockEntity) object;
				BlockEntityRenderer<BlockEntity> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(tile);
				if(renderer instanceof TyrannomationBlockmationRenderer) 
				{
					return ((TyrannomationBlockmationRenderer<?>) renderer).getGeoModelProvider();
				}
			}
			return null;
		});
	}

	private final TyrannomatedTyrannomationModel<T> modelProvider;

	public TyrannomationBlockmationRenderer(BlockEntityRenderDispatcher rendererDispatcherIn, TyrannomatedTyrannomationModel<T> modelProvider) 
	{
		this.modelProvider = modelProvider;
	}

	@Override
	public void render(BlockEntity tile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) 
	{
		this.render((T) tile, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
	}

	public void render(T tile, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) 
	{
		TyrannomationModel model = modelProvider.getModel(modelProvider.getModelLocation(tile));
		modelProvider.setLivingAnimations(tile, this.getUniqueID(tile));
		stack.pushPose();
		stack.translate(0, 0.01f, 0);
		stack.translate(0.5, 0, 0.5);

		rotateBlock(getFacing(tile), stack);

		RenderSystem.setShaderTexture(0, getTextureLocation(tile));
		Color renderColor = getRenderColor(tile, partialTicks, stack, bufferIn, null, packedLightIn);
		RenderType renderType = getRenderType(tile, partialTicks, stack, bufferIn, null, packedLightIn, getTextureLocation(tile));
		render(model, tile, partialTicks, renderType, stack, bufferIn, null, packedLightIn, OverlayTexture.NO_OVERLAY, (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
		stack.popPose();
	}

	@Override
	public TyrannomatedTyrannomationModel<T> getGeoModelProvider() 
	{
		return this.modelProvider;
	}

	protected void rotateBlock(Direction facing, PoseStack stack) 
	{
		switch(facing) 
		{
		case SOUTH:
			stack.mulPose(Vector3f.YP.rotationDegrees(180));
			break;
		case WEST:
			stack.mulPose(Vector3f.YP.rotationDegrees(90));
			break;
		case NORTH:
			stack.mulPose(Vector3f.YP.rotationDegrees(0));
			break;
		case EAST:
			stack.mulPose(Vector3f.YP.rotationDegrees(270));
			break;
		case UP:
			stack.mulPose(Vector3f.XP.rotationDegrees(90));
			break;
		case DOWN:
			stack.mulPose(Vector3f.XN.rotationDegrees(90));
			break;
		}
	}

	private Direction getFacing(T tile) 
	{
		BlockState blockState = tile.getBlockState();
		if(blockState.hasProperty(HorizontalDirectionalBlock.FACING)) 
		{
			return blockState.getValue(HorizontalDirectionalBlock.FACING);
		} 
		else if(blockState.hasProperty(DirectionalBlock.FACING)) 
		{
			return blockState.getValue(DirectionalBlock.FACING);
		} 
		else 
		{
			return Direction.NORTH;
		}
	}

	@Override
	public ResourceLocation getTextureLocation(T instance) 
	{
		return this.modelProvider.getTextureLocation(instance);
	}
}
