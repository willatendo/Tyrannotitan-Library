package tyrannimation.animation.render;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.mojang.math.Vector3f;

import tyrannimation.animation.raw.pojo.Bone;
import tyrannimation.animation.raw.pojo.Cube;
import tyrannimation.animation.raw.pojo.ModelProperties;
import tyrannimation.animation.raw.tree.RawBoneGroup;
import tyrannimation.animation.raw.tree.RawGeometryTree;
import tyrannimation.animation.render.built.TyrannomationBone;
import tyrannimation.animation.render.built.TyrannomationCube;
import tyrannimation.animation.render.built.TyrannomationModel;
import tyrannimation.util.VectorUtils;

public class TyrannomationBuilder implements ITyrannomationBuilder {
	private static Map<String, ITyrannomationBuilder> moddedGeoBuilders = new HashMap<>();
	private static ITyrannomationBuilder defaultBuilder = new TyrannomationBuilder();

	public static void registerGeoBuilder(String modID, ITyrannomationBuilder builder) {
		moddedGeoBuilders.put(modID, builder);
	}

	public static ITyrannomationBuilder getGeoBuilder(String modID) {
		ITyrannomationBuilder builder = moddedGeoBuilders.get(modID);
		return builder == null ? defaultBuilder : builder;
	}

	@Override
	public TyrannomationModel constructGeoModel(RawGeometryTree geometryTree) {
		TyrannomationModel model = new TyrannomationModel();
		model.properties = geometryTree.properties;
		for (RawBoneGroup rawBone : geometryTree.topLevelBones.values()) {
			model.topLevelBones.add(this.constructBone(rawBone, geometryTree.properties, null));
		}
		return model;
	}

	@Override
	public TyrannomationBone constructBone(RawBoneGroup bone, ModelProperties properties, TyrannomationBone parent) {
		TyrannomationBone geoBone = new TyrannomationBone();

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
				geoBone.childCubes.add(TyrannomationCube.createFromPojoCube(cube, properties, geoBone.inflate == null ? null : geoBone.inflate / 16, geoBone.mirror));
			}
		}

		for (RawBoneGroup child : bone.children.values()) {
			geoBone.childBones.add(constructBone(child, properties, geoBone));
		}

		return geoBone;
	}
}
