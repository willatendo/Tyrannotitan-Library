package tyrannotitanlib.core.client.chest;

import java.util.Calendar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import tyrannotitanlib.core.client.chest.TyrannoChestManager.ChestInfo;
import tyrannotitanlib.library.block.ITyrannoChestBlock;

public class TyrannoChestBlockEntityRender<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
	public static Block itemBlock = null;

	private final ModelPart lid;
	private final ModelPart bottom;
	private final ModelPart lock;
	private final ModelPart doubleLeftLid;
	private final ModelPart doubleLeftBottom;
	private final ModelPart doubleLeftLock;
	private final ModelPart doubleRightLid;
	private final ModelPart doubleRightBottom;
	private final ModelPart doubleRightLock;
	public boolean isChristmas;

	public TyrannoChestBlockEntityRender(BlockEntityRendererProvider.Context context) {
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) {
			this.isChristmas = true;
		}

		ModelPart modelpart = context.bakeLayer(ModelLayers.CHEST);
		this.bottom = modelpart.getChild("bottom");
		this.lid = modelpart.getChild("lid");
		this.lock = modelpart.getChild("lock");
		ModelPart modelpart1 = context.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
		this.doubleLeftBottom = modelpart1.getChild("bottom");
		this.doubleLeftLid = modelpart1.getChild("lid");
		this.doubleLeftLock = modelpart1.getChild("lock");
		ModelPart modelpart2 = context.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
		this.doubleRightBottom = modelpart2.getChild("bottom");
		this.doubleRightLid = modelpart2.getChild("lid");
		this.doubleRightLock = modelpart2.getChild("lock");
	}

	@Override
	public void render(T tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		Level world = tileEntity.getLevel();
		boolean flag = world != null;
		BlockState blockstate = flag ? tileEntity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
		ChestType chesttype = blockstate.hasProperty(ChestBlock.TYPE) ? blockstate.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
		Block block = blockstate.getBlock();
		if (block instanceof AbstractChestBlock) {
			AbstractChestBlock<?> abstractchestblock = (AbstractChestBlock) block;
			boolean flag1 = chesttype != ChestType.SINGLE;
			matrixStack.pushPose();
			float f = blockstate.getValue(ChestBlock.FACING).toYRot();
			matrixStack.translate(0.5D, 0.5D, 0.5D);
			matrixStack.mulPose(Vector3f.YP.rotationDegrees(-f));
			matrixStack.translate(-0.5D, -0.5D, -0.5D);
			DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> icallbackwrapper;
			if (flag) {
				icallbackwrapper = abstractchestblock.combine(blockstate, world, tileEntity.getBlockPos(), true);
			} else {
				icallbackwrapper = DoubleBlockCombiner.Combiner::acceptNone;
			}

			float f1 = icallbackwrapper.apply(ChestBlock.opennessCombiner(tileEntity)).get(partialTicks);
			f1 = 1.0F - f1;
			f1 = 1.0F - f1 * f1 * f1;
			int i = icallbackwrapper.apply(new BrightnessCombiner<>()).applyAsInt(combinedLight);
			VertexConsumer ivertexbuilder = this.getChestMaterial(tileEntity, chesttype).buffer(buffer, RenderType::entityCutout);
			if (flag1) {
				if (chesttype == ChestType.LEFT) {
					this.render(matrixStack, ivertexbuilder, this.doubleLeftLid, this.doubleLeftLock, this.doubleLeftBottom, f1, i, combinedOverlay);
				} else {
					this.render(matrixStack, ivertexbuilder, this.doubleRightLid, this.doubleRightLock, this.doubleRightBottom, f1, i, combinedOverlay);
				}
			} else {
				this.render(matrixStack, ivertexbuilder, this.lid, this.lock, this.bottom, f1, i, combinedOverlay);
			}

			matrixStack.popPose();
		}
	}

	public Material getChestMaterial(T t, ChestType type) {
		if (this.isChristmas) {
			return switch (type) {
			case SINGLE -> Sheets.CHEST_XMAS_LOCATION;
			case LEFT -> Sheets.CHEST_XMAS_LOCATION_LEFT;
			case RIGHT -> Sheets.CHEST_XMAS_LOCATION_RIGHT;
			};
		} else {
			Block inventoryBlock = itemBlock;
			if (inventoryBlock == null) {
				inventoryBlock = t.getBlockState().getBlock();
			}
			ChestInfo chestInfo = TyrannoChestManager.getInfoForChest(((ITyrannoChestBlock) inventoryBlock).getChestType());
			return switch (type) {
			case SINGLE -> chestInfo != null ? chestInfo.getSingleMaterial() : Sheets.CHEST_LOCATION;
			case LEFT -> chestInfo != null ? chestInfo.getLeftMaterial() : Sheets.CHEST_LOCATION_LEFT;
			case RIGHT -> chestInfo != null ? chestInfo.getRightMaterial() : Sheets.CHEST_LOCATION_RIGHT;
			};
		}
	}

	public void render(PoseStack stack, VertexConsumer builder, ModelPart chestLid, ModelPart chestLatch, ModelPart chestBottom, float lidAngle, int combinedLight, int combinedOverlay) {
		chestLid.xRot = -(lidAngle * ((float) Math.PI / 2F));
		chestLatch.xRot = chestLid.xRot;
		chestLid.render(stack, builder, combinedLight, combinedOverlay);
		chestLatch.render(stack, builder, combinedLight, combinedOverlay);
		chestBottom.render(stack, builder, combinedLight, combinedOverlay);
	}
}
