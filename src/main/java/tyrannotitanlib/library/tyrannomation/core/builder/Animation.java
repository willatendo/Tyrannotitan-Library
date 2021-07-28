package tyrannotitanlib.library.tyrannomation.core.builder;

import java.util.ArrayList;
import java.util.List;

import tyrannotitanlib.library.tyrannomation.core.keyframe.BoneAnimation;
import tyrannotitanlib.library.tyrannomation.core.keyframe.EventKeyFrame;
import tyrannotitanlib.library.tyrannomation.core.keyframe.ParticleEventKeyFrame;

public class Animation
{
	public String animationName;
	public Double animationLength;
	public boolean loop = true;
	public List<BoneAnimation> boneAnimations;
	public List<EventKeyFrame<String>> soundKeyFrames = new ArrayList<>();
	public List<ParticleEventKeyFrame> particleKeyFrames = new ArrayList<>();
	public List<EventKeyFrame<List<String>>> customInstructionKeyframes = new ArrayList<>();
}
