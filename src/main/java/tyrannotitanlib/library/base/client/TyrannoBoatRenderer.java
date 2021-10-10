package tyrannotitanlib.library.base.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import tyrannotitanlib.library.base.entity.TyrannoBoatEntity;

public class TyrannoBoatRenderer extends EntityRenderer<TyrannoBoatEntity> 
{
	private final BoatModel model = new BoatModel();

	public TyrannoBoatRenderer(EntityRendererManager renderManager) 
	{
		super(renderManager);
		this.shadowRadius = 0.8F;
	}

	@Override
	public void render(TyrannoBoatEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) 
	{
		matrixStack.pushPose();
		matrixStack.translate(0.0D, 0.375D, 0.0D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		float f = (float) entity.getHurtTime() - partialTicks;
		float f1 = entity.getDamage() - partialTicks;
		if(f1 < 0.0F) 
		{
			f1 = 0.0F;
		}

		if(f > 0.0F) 
		{
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(f) * f * f1 / 10.0F * (float) entity.getHurtDir()));
		}

		float f2 = entity.getBubbleAngle(partialTicks);
		if(!MathHelper.equal(f2, 0.0F)) 
		{
			matrixStack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entity.getBubbleAngle(partialTicks), true));
		}

		matrixStack.scale(-1.0F, -1.0F, 1.0F);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		this.model.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
		IVertexBuilder ivertexbuilder = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
		this.model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		IVertexBuilder ivertexbuilder1 = buffer.getBuffer(RenderType.waterMask());
		this.model.waterPatch().render(matrixStack, ivertexbuilder1, packedLight, OverlayTexture.NO_OVERLAY);
		matrixStack.popPose();
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(TyrannoBoatEntity entity) 
	{
		return entity.getBoat().getTexture();
	}

}
