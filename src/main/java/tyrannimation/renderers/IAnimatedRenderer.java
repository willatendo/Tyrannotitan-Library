package tyrannimation.renderers;

import java.awt.Color;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import tyrannimation.animation.render.built.TyrannomationBone;
import tyrannimation.animation.render.built.TyrannomationCube;
import tyrannimation.animation.render.built.TyrannomationModel;
import tyrannimation.animation.render.built.TyrannomationQuad;
import tyrannimation.animation.render.built.TyrannomationVertex;
import tyrannimation.model.provider.AnimatedModelProvider;
import tyrannimation.util.RenderUtils;

public interface IAnimatedRenderer<T> {
	default void render(TyrannomationModel model, T animated, float partialTicks, RenderType type, PoseStack stack, @Nullable MultiBufferSource buffer, @Nullable VertexConsumer vertex, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.renderEarly(animated, stack, partialTicks, buffer, vertex, packedLight, packedOverlay, red, green, blue, alpha);

		if (buffer != null) {
			vertex = buffer.getBuffer(type);
		}

		this.renderLate(animated, stack, partialTicks, buffer, vertex, packedLight, packedOverlay, red, green, blue, alpha);

		for (TyrannomationBone group : model.topLevelBones) {
			renderRecursively(group, stack, vertex, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}

	default void renderRecursively(TyrannomationBone bone, PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		stack.pushPose();
		RenderUtils.translate(bone, stack);
		RenderUtils.moveToPivot(bone, stack);
		RenderUtils.rotate(bone, stack);
		RenderUtils.scale(bone, stack);
		RenderUtils.moveBackFromPivot(bone, stack);

		if (!bone.isHidden) {
			for (TyrannomationCube cube : bone.childCubes) {
				stack.pushPose();
				renderCube(cube, stack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
				stack.popPose();
			}
			for (TyrannomationBone childBone : bone.childBones) {
				renderRecursively(childBone, stack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			}
		}

		stack.popPose();
	}

	default void renderCube(TyrannomationCube cube, PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.moveToPivot(cube, stack);
		RenderUtils.rotate(cube, stack);
		RenderUtils.moveBackFromPivot(cube, stack);
		Matrix3f matrix3f = stack.last().normal();
		Matrix4f matrix4f = stack.last().pose();

		for (TyrannomationQuad quad : cube.quads) {
			if (quad == null) {
				continue;
			}
			Vector3f normal = quad.normal.copy();
			normal.transform(matrix3f);

			if ((cube.size.y() == 0 || cube.size.z() == 0) && normal.x() < 0) {
				normal.mul(-1, 1, 1);
			}
			if ((cube.size.x() == 0 || cube.size.z() == 0) && normal.y() < 0) {
				normal.mul(1, -1, 1);
			}
			if ((cube.size.x() == 0 || cube.size.y() == 0) && normal.z() < 0) {
				normal.mul(1, 1, -1);
			}

			for (TyrannomationVertex vertex : quad.vertices) {
				Vector4f vector4f = new Vector4f(vertex.position.x(), vertex.position.y(), vertex.position.z(), 1.0F);
				vector4f.transform(matrix4f);
				buffer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), red, green, blue, alpha, vertex.textureU, vertex.textureV, packedOverlay, packedLight, normal.x(), normal.y(), normal.z());
			}
		}
	}

	AnimatedModelProvider getAnimatedModelProvider();

	ResourceLocation getTextureLocation(T instance);

	default void renderEarly(T animated, PoseStack stackIn, float ticks, @Nullable MultiBufferSource buffer, @Nullable VertexConsumer vertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
	}

	default void renderLate(T animated, PoseStack stackIn, float ticks, MultiBufferSource buffer, VertexConsumer vertex, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
	}

	default RenderType getRenderType(T animated, float partialTicks, PoseStack stack, @Nullable MultiBufferSource buffer, @Nullable VertexConsumer vertexBuilder, int packedLight, ResourceLocation textureLocation) {
		return RenderType.entityCutout(textureLocation);
	}

	default Color getRenderColor(T animated, float partialTicks, PoseStack stack, @Nullable MultiBufferSource buffer, @Nullable VertexConsumer vertexBuilder, int packedLight) {
		return new Color(255, 255, 255, 255);
	}

	default Integer getUniqueID(T animated) {
		return animated.hashCode();
	}
}
