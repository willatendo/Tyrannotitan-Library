package tyrannotitanlib.library.tyrannomationcore.keyframe;

import tyrannotitanlib.library.tyrannomationcore.processor.IBone;

public class BoneTyrannomationQueue {
	public final IBone bone;
	public TyrannomationPointQueue rotationXQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue rotationYQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue rotationZQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue positionXQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue positionYQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue positionZQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue scaleXQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue scaleYQueue = new TyrannomationPointQueue();
	public TyrannomationPointQueue scaleZQueue = new TyrannomationPointQueue();

	public BoneTyrannomationQueue(IBone bone) {
		this.bone = bone;
	}
}
