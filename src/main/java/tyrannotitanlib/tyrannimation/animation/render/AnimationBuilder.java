package tyrannotitanlib.tyrannimation.animation.render;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.mojang.math.Vector3f;

import tyrannotitanlib.tyrannimation.animation.raw.pojo.Bone;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.Cube;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.ModelProperties;
import tyrannotitanlib.tyrannimation.animation.raw.tree.RawBoneGroup;
import tyrannotitanlib.tyrannimation.animation.raw.tree.RawGeometryTree;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationBone;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationCube;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationModel;
import tyrannotitanlib.tyrannimation.util.VectorUtils;

public class AnimationBuilder implements IAnimationBuilder {
	private static Map<String, IAnimationBuilder> moddedGeoBuilders = new HashMap<>();
	private static IAnimationBuilder defaultBuilder = new AnimationBuilder();

	public static void registerGeoBuilder(String modID, IAnimationBuilder builder) {
		moddedGeoBuilders.put(modID, builder);
	}

	public static IAnimationBuilder getGeoBuilder(String modID) {
		IAnimationBuilder builder = moddedGeoBuilders.get(modID);
		return builder == null ? defaultBuilder : builder;
	}

	@Override
	public AnimationModel constructGeoModel(RawGeometryTree geometryTree) {
		AnimationModel model = new AnimationModel();
		model.properties = geometryTree.properties;
		for (RawBoneGroup rawBone : geometryTree.topLevelBones.values()) {
			model.topLevelBones.add(this.constructBone(rawBone, geometryTree.properties, null));
		}
		return model;
	}

	@Override
	public AnimationBone constructBone(RawBoneGroup bone, ModelProperties properties, AnimationBone parent) {
		AnimationBone geoBone = new AnimationBone();

		Bone rawBone = bone.selfBone;
		Vector3f rotation = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(rawBone.getRotation()));
		Vector3f pivot = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(rawBone.getPivot()));
		rotation.mul(-1, -1, 1);

		geoBone.mirror = rawBone.getMirror();
		geoBone.dontRender = rawBone.getNeverRender();
		geoBone.reset = rawBone.getReset();
		geoBone.inflate = rawBone.getInflate();
		geoBone.parent = parent;
		geoBone.setModelRendererName(rawBone.getName());

		geoBone.setRotationX((float) Math.toRadians(rotation.x()));
		geoBone.setRotationY((float) Math.toRadians(rotation.y()));
		geoBone.setRotationZ((float) Math.toRadians(rotation.z()));

		geoBone.rotationPointX = -pivot.x();
		geoBone.rotationPointY = pivot.y();
		geoBone.rotationPointZ = pivot.z();

		if (!ArrayUtils.isEmpty(rawBone.getCubes())) {
			for (Cube cube : rawBone.getCubes()) {
				geoBone.childCubes.add(AnimationCube.createFromPojoCube(cube, properties, geoBone.inflate == null ? null : geoBone.inflate / 16, geoBone.mirror));
			}
		}

		for (RawBoneGroup child : bone.children.values()) {
			geoBone.childBones.add(constructBone(child, properties, geoBone));
		}

		return geoBone;
	}
}
