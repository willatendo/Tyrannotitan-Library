package tyrannotitanlib.library.tyrannomationcore.builder;

import java.util.ArrayList;
import java.util.List;

import tyrannotitanlib.library.tyrannomationcore.keyframe.BoneTyrannomation;
import tyrannotitanlib.library.tyrannomationcore.keyframe.EventKeyFrame;
import tyrannotitanlib.library.tyrannomationcore.keyframe.ParticleEventKeyFrame;

public class Tyrannomation {
	public String animationName;
	public Double animationLength;
	public boolean loop = true;
	public List<BoneTyrannomation> boneAnimations;
	public List<EventKeyFrame<String>> soundKeyFrames = new ArrayList<>();
	public List<ParticleEventKeyFrame> particleKeyFrames = new ArrayList<>();
	public List<EventKeyFrame<List<String>>> customInstructionKeyframes = new ArrayList<>();
}
