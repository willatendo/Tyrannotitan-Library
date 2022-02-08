package tyrannimation.core.manager;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import tyrannimation.core.controller.TyrannomationController;
import tyrannimation.core.processor.IAnimatedBone;
import tyrannimation.core.snapshot.BoneSnapshot;

public class AnimatedData {
	private HashMap<String, Pair<IAnimatedBone, BoneSnapshot>> boneSnapshotCollection;
	private HashMap<String, TyrannomationController> animationControllers = new HashMap<>();
	public double tick;
	public boolean isFirstTick = true;
	private double resetTickLength = 1;
	public Double startTick;
	public Object ticker;
	public boolean shouldPlayWhilePaused = false;

	public AnimatedData() {
		super();
		this.boneSnapshotCollection = new HashMap<>();
	}

	public TyrannomationController addAnimatedController(TyrannomationController value) {
		return this.animationControllers.put(value.getName(), value);
	}

	public HashMap<String, Pair<IAnimatedBone, BoneSnapshot>> getBoneSnapshotCollection() {
		return this.boneSnapshotCollection;
	}

	public void setBoneSnapshotCollection(HashMap<String, Pair<IAnimatedBone, BoneSnapshot>> boneSnapshotCollection) {
		this.boneSnapshotCollection = boneSnapshotCollection;
	}

	public void clearSnapshotCache() {
		this.boneSnapshotCollection = new HashMap<>();
	}

	public double getResetSpeed() {
		return this.resetTickLength;
	}

	public void setResetSpeedInTicks(double resetTickLength) {
		this.resetTickLength = resetTickLength < 0 ? 0 : resetTickLength;
	}

	public HashMap<String, TyrannomationController> getAnimatedControllers() {
		return this.animationControllers;
	}
}
