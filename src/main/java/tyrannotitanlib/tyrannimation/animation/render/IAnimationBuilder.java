package tyrannotitanlib.tyrannimation.animation.render;

import tyrannotitanlib.tyrannimation.animation.raw.pojo.ModelProperties;
import tyrannotitanlib.tyrannimation.animation.raw.tree.RawBoneGroup;
import tyrannotitanlib.tyrannimation.animation.raw.tree.RawGeometryTree;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationBone;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationModel;

public interface IAnimationBuilder {
	AnimationModel constructGeoModel(RawGeometryTree geometryTree);

	AnimationBone constructBone(RawBoneGroup bone, ModelProperties properties, AnimationBone parent);
}
