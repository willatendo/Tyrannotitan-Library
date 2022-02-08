package tyrannimation.animation.raw.tree;

import java.util.HashMap;

import tyrannimation.animation.raw.pojo.Bone;

public class RawBoneGroup {
	public HashMap<String, RawBoneGroup> children = new HashMap<>();
	public Bone selfBone;

	public RawBoneGroup(Bone bone) {
		this.selfBone = bone;
	}
}
