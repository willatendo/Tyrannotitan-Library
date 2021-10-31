package tyrannotitanlib.content.client.chest;

import java.util.function.Supplier;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TyrannoChestItemRenderer<T extends TileEntity> extends ItemStackTileEntityRenderer
{
	private final Supplier<T> te;

	public TyrannoChestItemRenderer(Supplier<T> te) 
	{
		this.te = te;
	}

	@Override
	public void renderByItem(ItemStack stack, TransformType transformType, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) 
	{
		BlockItem blockItem = (BlockItem) stack.getItem();
		TyrannoChestBlockEntityRender.itemBlock = blockItem.getBlock();
		TileEntityRendererDispatcher.instance.renderItem(this.te.get(), matrix, buffer, combinedLight, combinedOverlay);
		TyrannoChestBlockEntityRender.itemBlock = null;
	}
}
