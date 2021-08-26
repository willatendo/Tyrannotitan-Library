package tyrannotitanlib.library.tyrannomation.core.manager;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.Pair;

import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.core.processor.IBone;
import tyrannotitanlib.library.tyrannomation.core.snapshot.BoneSnapshot;

public class TyrannomationData
{
	private HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection;
	private HashMap<String, TyrannomationController> animationControllers = new HashMap<>();
	public double tick;
	public boolean isFirstTick = true;
	private double resetTickLength = 1;
	public Double startTick;
	public Object ticker;
	public boolean shouldPlayWhilePaused = false;

	public TyrannomationData()
	{
		super();
		boneSnapshotCollection = new HashMap<>();
	}

	public TyrannomationController addAnimationController(TyrannomationController value)
	{
		return this.animationControllers.put(value.getName(), value);
	}

	public HashMap<String, Pair<IBone, BoneSnapshot>> getBoneSnapshotCollection()
	{
		return boneSnapshotCollection;
	}

	public void setBoneSnapshotCollection(HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection)
	{
		this.boneSnapshotCollection = boneSnapshotCollection;
	}

	public void clearSnapshotCache()
	{
		this.boneSnapshotCollection = new HashMap<>();
	}


	public double getResetSpeed()
	{
		return resetTickLength;
	}

	public void setResetSpeedInTicks(double resetTickLength)
	{
		this.resetTickLength = resetTickLength < 0 ? 0 : resetTickLength;
	}

	public HashMap<String, TyrannomationController> getAnimationControllers()
	{
		return animationControllers;
	}
}
