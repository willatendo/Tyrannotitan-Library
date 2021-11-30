package tyrannotitanlib.library.tyrannomation.renderers;

import java.awt.Color;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationBone;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationCube;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationQuad;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationVertex;
import tyrannotitanlib.library.tyrannomation.util.RenderUtils;

public interface ITyrannomationRenderer<T> 
{
	default void render(TyrannomationModel model, T animatable, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) 
	{
		renderEarly(animatable, matrixStackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);

		if(renderTypeBuffer != null) 
		{
			vertexBuilder = renderTypeBuffer.getBuffer(type);
		}
		renderLate(animatable, matrixStackIn, partialTicks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		for(TyrannomationBone group : model.topLevelBones) 
		{
			renderRecursively(group, matrixStackIn, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		}
	}

	default void renderRecursively(TyrannomationBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) 
	{
		stack.pushPose();
		RenderUtils.translate(bone, stack);
		RenderUtils.moveToPivot(bone, stack);
		RenderUtils.rotate(bone, stack);
		RenderUtils.scale(bone, stack);
		RenderUtils.moveBackFromPivot(bone, stack);

		if(!bone.isHidden) 
		{
			for(TyrannomationCube cube : bone.childCubes) 
			{
				stack.pushPose();
				renderCube(cube, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
				stack.popPose();
			}
			for(TyrannomationBone childBone : bone.childBones) 
			{
				renderRecursively(childBone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
			}
		}

		stack.popPose();
	}

	default void renderCube(TyrannomationCube cube, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) 
	{
		RenderUtils.moveToPivot(cube, stack);
		RenderUtils.rotate(cube, stack);
		RenderUtils.moveBackFromPivot(cube, stack);
		Matrix3f matrix3f = stack.last().normal();
		Matrix4f matrix4f = stack.last().pose();

		for(TyrannomationQuad quad : cube.quads) 
		{
			if(quad == null) 
			{
				continue;
			}
			Vector3f normal = quad.normal.copy();
			normal.transform(matrix3f);

			if((cube.size.y() == 0 || cube.size.z() == 0) && normal.x() < 0) 
			{
				normal.mul(-1, 1, 1);
			}
			if((cube.size.x() == 0 || cube.size.z() == 0) && normal.y() < 0) 
			{
				normal.mul(1, -1, 1);
			}
			if((cube.size.x() == 0 || cube.size.y() == 0) && normal.z() < 0) 
			{
				normal.mul(1, 1, -1);
			}

			for(TyrannomationVertex vertex : quad.vertices) 
			{
				Vector4f vector4f = new Vector4f(vertex.position.x(), vertex.position.y(), vertex.position.z(), 1.0F);
				vector4f.transform(matrix4f);
				bufferIn.vertex(vector4f.x(), vector4f.y(), vector4f.z(), red, green, blue, alpha, vertex.textureU, vertex.textureV, packedOverlayIn, packedLightIn, normal.x(), normal.y(), normal.z());
			}
		}
	}

	TyrannomationModelProvider getGeoModelProvider();

	ResourceLocation getTextureLocation(T instance);

	default void renderEarly(T animatable, PoseStack stackIn, float ticks, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) { }

	default void renderLate(T animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) { }

	default RenderType getRenderType(T animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) 
	{
		return RenderType.entityCutout(textureLocation);
	}

	default Color getRenderColor(T animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn) 
	{
		return new Color(255, 255, 255, 255);
	}

	default Integer getUniqueID(T animatable) 
	{
		return animatable.hashCode();
	}
}
