package tyrannotitanlib.library.tyrannomation.tyranno.render;

import tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo.ModelProperties;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.tree.RawBoneGroup;
import tyrannotitanlib.library.tyrannomation.tyranno.raw.tree.RawGeometryTree;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationBone;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;

public interface ITyrannomationBuilder 
{
	TyrannomationModel constructGeoModel(RawGeometryTree geometryTree);

	TyrannomationBone constructBone(RawBoneGroup bone, ModelProperties properties, TyrannomationBone parent);
}
