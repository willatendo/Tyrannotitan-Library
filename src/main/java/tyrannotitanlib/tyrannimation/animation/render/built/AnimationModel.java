package tyrannotitanlib.tyrannimation.animation.render.built;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tyrannotitanlib.tyrannimation.animation.raw.pojo.ModelProperties;

public class AnimationModel {
	public List<AnimationBone> topLevelBones = new ArrayList<>();
	public ModelProperties properties;

	public Optional<AnimationBone> getBone(String name) {
		for (AnimationBone bone : topLevelBones) {
			AnimationBone optionalBone = getBoneRecursively(name, bone);
			if (optionalBone != null) {
				return Optional.of(optionalBone);
			}
		}
		return Optional.empty();
	}

	private AnimationBone getBoneRecursively(String name, AnimationBone bone) {
		if (bone.name.equals(name)) {
			return bone;
		}
		for (AnimationBone childBone : bone.childBones) {
			if (childBone.name.equals(name)) {
				return childBone;
			}
			AnimationBone optionalBone = getBoneRecursively(name, childBone);
			if (optionalBone != null) {
				return optionalBone;
			}
		}
		return null;
	}
}
