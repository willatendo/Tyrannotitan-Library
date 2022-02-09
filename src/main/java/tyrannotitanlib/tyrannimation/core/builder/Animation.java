package tyrannotitanlib.tyrannimation.core.builder;

import java.util.ArrayList;
import java.util.List;

import tyrannotitanlib.tyrannimation.core.keyframe.AnimatedBone;
import tyrannotitanlib.tyrannimation.core.keyframe.EventKeyFrame;
import tyrannotitanlib.tyrannimation.core.keyframe.ParticleEventKeyFrame;

public class Animation {
	public String animationName;
	public Double animationLength;
	public boolean loop = true;
	public List<AnimatedBone> boneAnimations;
	public List<EventKeyFrame<String>> soundKeyFrames = new ArrayList<>();
	public List<ParticleEventKeyFrame> particleKeyFrames = new ArrayList<>();
	public List<EventKeyFrame<List<String>>> customInstructionKeyframes = new ArrayList<>();
}
