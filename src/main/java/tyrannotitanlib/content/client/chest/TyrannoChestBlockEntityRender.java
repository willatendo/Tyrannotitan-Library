package tyrannotitanlib.content.client.chest;

import java.util.Calendar;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import tyrannotitanlib.library.base.block.ITyrannoChestBlock;

public class TyrannoChestBlockEntityRender<T extends TileEntity & IChestLid> extends TileEntityRenderer<T> 
{
	public static Block itemBlock = null;

	public final ModelRenderer singleLid;
	public final ModelRenderer singleBottom;
	public final ModelRenderer singleLatch;
	public final ModelRenderer rightLid;
	public final ModelRenderer rightBottom;
	public final ModelRenderer rightLatch;
	public final ModelRenderer leftLid;
	public final ModelRenderer leftBottom;
	public final ModelRenderer leftLatch;
	public boolean isChristmas;

	public TyrannoChestBlockEntityRender(TileEntityRendererDispatcher rendererDispatcher) 
	{
		super(rendererDispatcher);
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) 
		{
			this.isChristmas = true;
		}

		this.singleBottom = new ModelRenderer(64, 64, 0, 19);
		this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
		this.singleLid = new ModelRenderer(64, 64, 0, 0);
		this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
		this.singleLid.y = 9.0F;
		this.singleLid.z = 1.0F;
		this.singleLatch = new ModelRenderer(64, 64, 0, 0);
		this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
		this.singleLatch.y = 8.0F;
		this.rightBottom = new ModelRenderer(64, 64, 0, 19);
		this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
		this.rightLid = new ModelRenderer(64, 64, 0, 0);
		this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
		this.rightLid.y = 9.0F;
		this.rightLid.z = 1.0F;
		this.rightLatch = new ModelRenderer(64, 64, 0, 0);
		this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
		this.rightLatch.y = 8.0F;
		this.leftBottom = new ModelRenderer(64, 64, 0, 19);
		this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
		this.leftLid = new ModelRenderer(64, 64, 0, 0);
		this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
		this.leftLid.y = 9.0F;
		this.leftLid.z = 1.0F;
		this.leftLatch = new ModelRenderer(64, 64, 0, 0);
		this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
		this.leftLatch.y = 8.0F;
	}

	public void render(T tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) 
	{
		World world = tileEntity.getLevel();
		boolean flag = world != null;
		BlockState blockstate = flag ? tileEntity.getBlockState()	: Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
		ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
		Block block = blockstate.getBlock();
		if(block instanceof AbstractChestBlock) 
		{
			AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock) block;
			boolean flag1 = chesttype != ChestType.SINGLE;
			matrixStack.pushPose();
			float f = blockstate.getValue(ChestBlock.FACING).toYRot();
			matrixStack.translate(0.5D, 0.5D, 0.5D);
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(-f));
			matrixStack.translate(-0.5D, -0.5D, -0.5D);
			TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> icallbackwrapper;
			if(flag) 
			{
				icallbackwrapper = abstractchestblock.combine(blockstate, world, tileEntity.getBlockPos(), true);
			} 
			else 
			{
				icallbackwrapper = TileEntityMerger.ICallback::acceptNone;
			}

			float f1 = icallbackwrapper.apply(ChestBlock.opennessCombiner(tileEntity)).get(partialTicks);
			f1 = 1.0F - f1;
			f1 = 1.0F - f1 * f1 * f1;
			int i = icallbackwrapper.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLight);
			IVertexBuilder ivertexbuilder = this.getChestMaterial(tileEntity, chesttype).buffer(buffer,
					RenderType::entityCutout);
			if(flag1) 
			{
				if(chesttype == ChestType.LEFT) 
				{
					this.render(matrixStack, ivertexbuilder, this.leftLid, this.leftLatch, this.leftBottom, f1, i, combinedOverlay);
				} 
				else 
				{
					this.render(matrixStack, ivertexbuilder, this.rightLid, this.rightLatch, this.rightBottom, f1, i, combinedOverlay);
				}
			} 
			else 
			{
				this.render(matrixStack, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, combinedOverlay);
			}

			matrixStack.popPose();
		}
	}

	public RenderMaterial getChestMaterial(T t, ChestType type) 
	{
		if(this.isChristmas) 
		{
			switch (type) 
			{
			default:
			case SINGLE:
				return Atlases.CHEST_XMAS_LOCATION;
			case LEFT:
				return Atlases.CHEST_XMAS_LOCATION_LEFT;
			case RIGHT:
				return Atlases.CHEST_XMAS_LOCATION_RIGHT;
			}
		} 
		else 
		{
			Block inventoryBlock = itemBlock;
			if(inventoryBlock == null)
				inventoryBlock = t.getBlockState().getBlock();
			TyrannoChestManager.ChestInfo chestfo = TyrannoChestManager.getInfoForChest(((ITyrannoChestBlock) inventoryBlock).getChestType());
			switch(type) 
			{
			default:
			case SINGLE:
				return chestfo != null ? chestfo.getSingleMaterial() : Atlases.CHEST_LOCATION;
			case LEFT:
				return chestfo != null ? chestfo.getLeftMaterial() : Atlases.CHEST_LOCATION_LEFT;
			case RIGHT:
				return chestfo != null ? chestfo.getRightMaterial() : Atlases.CHEST_LOCATION_RIGHT;
			}
		}
	}

	public void render(MatrixStack matrixStack, IVertexBuilder builder, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, float lidAngle, int combinedLight, int combinedOverlay) 
	{
		chestLid.xRot = -(lidAngle * ((float) Math.PI / 2F));
		chestLatch.xRot = chestLid.xRot;
		chestLid.render(matrixStack, builder, combinedLight, combinedOverlay);
		chestLatch.render(matrixStack, builder, combinedLight, combinedOverlay);
		chestBottom.render(matrixStack, builder, combinedLight, combinedOverlay);
	}
}
