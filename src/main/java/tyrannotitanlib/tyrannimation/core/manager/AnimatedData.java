package tyrannotitanlib.tyrannimation.core.manager;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import tyrannotitanlib.tyrannimation.core.controller.AnimationController;
import tyrannotitanlib.tyrannimation.core.processor.IAnimatedBone;
import tyrannotitanlib.tyrannimation.core.snapshot.BoneSnapshot;

public class AnimatedData {
	private HashMap<String, Pair<IAnimatedBone, BoneSnapshot>> boneSnapshotCollection;
	private HashMap<String, AnimationController> animationControllers = new HashMap<>();
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

	public AnimationController addAnimatedController(AnimationController value) {
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

	public HashMap<String, AnimationController> getAnimatedControllers() {
		return this.animationControllers;
	}
}
