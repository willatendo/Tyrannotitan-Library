package tyrannimation.core.builder;

import java.util.ArrayList;
import java.util.List;

import tyrannimation.core.keyframe.AnimatedBone;
import tyrannimation.core.keyframe.EventKeyFrame;
import tyrannimation.core.keyframe.ParticleEventKeyFrame;

public class Tyrannomation {
	public String animationName;
	public Double animationLength;
	public boolean loop = true;
	public List<AnimatedBone> boneAnimations;
	public List<EventKeyFrame<String>> soundKeyFrames = new ArrayList<>();
	public List<ParticleEventKeyFrame> particleKeyFrames = new ArrayList<>();
	public List<EventKeyFrame<List<String>>> customInstructionKeyframes = new ArrayList<>();
}
