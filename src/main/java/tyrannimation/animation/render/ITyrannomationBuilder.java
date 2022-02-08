package tyrannimation.animation.render;

import tyrannimation.animation.raw.pojo.ModelProperties;
import tyrannimation.animation.raw.tree.RawBoneGroup;
import tyrannimation.animation.raw.tree.RawGeometryTree;
import tyrannimation.animation.render.built.TyrannomationBone;
import tyrannimation.animation.render.built.TyrannomationModel;

public interface ITyrannomationBuilder {
	TyrannomationModel constructGeoModel(RawGeometryTree geometryTree);

	TyrannomationBone constructBone(RawBoneGroup bone, ModelProperties properties, TyrannomationBone parent);
}
