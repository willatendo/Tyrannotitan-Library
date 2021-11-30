package tyrannotitanlib.content.client.chest;

import java.util.function.Supplier;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TyrannoChestItemRenderer<T extends BlockEntity> extends BlockEntityWithoutLevelRenderer
{
	private final Supplier<T> blockEntity;

	public TyrannoChestItemRenderer(Supplier<T> blockEntity) 
	{
		this.blockEntity = blockEntity;
	}

	@Override
	public void renderByItem(ItemStack stack, TransformType transformType, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int combinedOverlay) 
	{
		BlockItem blockItem = (BlockItem) stack.getItem();
		TyrannoChestBlockEntityRender.itemBlock = blockItem.getBlock();
		BlockEntityRenderDispatcher.instance.renderItem(this.blockEntity.get(), matrix, buffer, combinedLight, combinedOverlay);
		TyrannoChestBlockEntityRender.itemBlock = null;
	}
}
