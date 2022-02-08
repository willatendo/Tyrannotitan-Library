package tyrannimation.core.keyframe;

import tyrannimation.core.processor.IAnimatedBone;

public class AnimatedBoneQueue {
	public final IAnimatedBone animatedBone;
	public TyrannomationPointQueue rotationXQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue rotationYQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue rotationZQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue positionXQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue positionYQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue positionZQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue scaleXQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue scaleYQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue scaleZQueue = new TyrannomationPointQueue();

	public AnimatedBoneQueue(IAnimatedBone animatedBone) {
		this.animatedBone = animatedBone;
	}
}
