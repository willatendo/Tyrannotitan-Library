package tyrannotitanlib.tyrannimation.core.keyframe;

import tyrannotitanlib.tyrannimation.core.processor.IAnimatedBone;

public class AnimatedBoneQueue {
	public final IAnimatedBone animatedBone;
	public AnimationPointQueue rotationXQueue = new AnimationPointQueue();
	public AnimationPointQueue rotationYQueue = new AnimationPointQueue();
	public AnimationPointQueue rotationZQueue = new AnimationPointQueue();
	public AnimationPointQueue positionXQueue = new AnimationPointQueue();
	public AnimationPointQueue positionYQueue = new AnimationPointQueue();
	public AnimationPointQueue positionZQueue = new AnimationPointQueue();
	public AnimationPointQueue scaleXQueue = new AnimationPointQueue();
	public AnimationPointQueue scaleYQueue = new AnimationPointQueue();
	public AnimationPointQueue scaleZQueue = new AnimationPointQueue();

	public AnimatedBoneQueue(IAnimatedBone animatedBone) {
		this.animatedBone = animatedBone;
	}
}
