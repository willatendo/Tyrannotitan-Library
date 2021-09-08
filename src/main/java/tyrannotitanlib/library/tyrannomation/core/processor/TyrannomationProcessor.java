package tyrannotitanlib.library.tyrannomation.core.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.eliotlash.molang.MolangParser;

import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatableModel;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.core.keyframe.TyrannomationPoint;
import tyrannotitanlib.library.tyrannomation.core.keyframe.BoneTyrannomationQueue;
import tyrannotitanlib.library.tyrannomation.core.manager.TyrannomationData;
import tyrannotitanlib.library.tyrannomation.core.snapshot.BoneSnapshot;
import tyrannotitanlib.library.tyrannomation.core.snapshot.DirtyTracker;
import tyrannotitanlib.library.tyrannomation.core.util.MathUtil;

public class TyrannomationProcessor<T extends ITyrannomatable>
{
	public boolean reloadAnimations = false;
	private List<IBone> modelRendererList = new ArrayList();
	private double lastTickValue = -1;
	private final ITyrannomatableModel animatedModel;

	public TyrannomationProcessor(ITyrannomatableModel animatedModel)
	{
		this.animatedModel = animatedModel;
	}

	public void tickAnimation(ITyrannomatable entity, Integer uniqueID, double seekTime, TyrannomationEvent event, MolangParser parser, boolean crashWhenCantFindBone)
	{
		if(seekTime == lastTickValue)
		{
			return;
		}
		
		lastTickValue = seekTime;
		TyrannomationData manager = entity.getFactory().getOrCreateAnimationData(uniqueID);
		HashMap<String, DirtyTracker> modelTracker = createNewDirtyTracker();

		updateBoneSnapshots(manager.getBoneSnapshotCollection());

		HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshots = manager.getBoneSnapshotCollection();

		for(TyrannomationController<T> controller : manager.getAnimationControllers().values())
		{
			if(reloadAnimations)
			{
				controller.markNeedsReload();
				controller.getBoneAnimationQueues().clear();
			}

			controller.isJustStarting = manager.isFirstTick;

			event.setController(controller);

			controller.process(seekTime, event, modelRendererList, boneSnapshots, parser, crashWhenCantFindBone);

			for(BoneTyrannomationQueue boneAnimation : controller.getBoneAnimationQueues().values())
			{
				IBone bone = boneAnimation.bone;
				BoneSnapshot snapshot = boneSnapshots.get(bone.getName()).getRight();
				BoneSnapshot initialSnapshot = bone.getInitialSnapshot();

				TyrannomationPoint rXPoint = boneAnimation.rotationXQueue.poll();
				TyrannomationPoint rYPoint = boneAnimation.rotationYQueue.poll();
				TyrannomationPoint rZPoint = boneAnimation.rotationZQueue.poll();

				TyrannomationPoint pXPoint = boneAnimation.positionXQueue.poll();
				TyrannomationPoint pYPoint = boneAnimation.positionYQueue.poll();
				TyrannomationPoint pZPoint = boneAnimation.positionZQueue.poll();

				TyrannomationPoint sXPoint = boneAnimation.scaleXQueue.poll();
				TyrannomationPoint sYPoint = boneAnimation.scaleYQueue.poll();
				TyrannomationPoint sZPoint = boneAnimation.scaleZQueue.poll();

				DirtyTracker dirtyTracker = modelTracker.get(bone.getName());
				if(dirtyTracker == null)
				{
					continue;
				}
				
				if(rXPoint != null && rYPoint != null && rZPoint != null)
				{
					bone.setRotationX(MathUtil.lerpValues(rXPoint, controller.easingType, controller.customEasingMethod) + initialSnapshot.rotationValueX);
					bone.setRotationY(MathUtil.lerpValues(rYPoint, controller.easingType, controller.customEasingMethod) + initialSnapshot.rotationValueY);
					bone.setRotationZ(MathUtil.lerpValues(rZPoint, controller.easingType, controller.customEasingMethod) + initialSnapshot.rotationValueZ);
					snapshot.rotationValueX = bone.getRotationX();
					snapshot.rotationValueY = bone.getRotationY();
					snapshot.rotationValueZ = bone.getRotationZ();
					snapshot.isCurrentlyRunningRotationAnimation = true;
					dirtyTracker.hasRotationChanged = true;
				}

				if(pXPoint != null && pYPoint != null && pZPoint != null)
				{
					bone.setPositionX(MathUtil.lerpValues(pXPoint, controller.easingType, controller.customEasingMethod));
					bone.setPositionY(MathUtil.lerpValues(pYPoint, controller.easingType, controller.customEasingMethod));
					bone.setPositionZ(MathUtil.lerpValues(pZPoint, controller.easingType, controller.customEasingMethod));
					snapshot.positionOffsetX = bone.getPositionX();
					snapshot.positionOffsetY = bone.getPositionY();
					snapshot.positionOffsetZ = bone.getPositionZ();
					snapshot.isCurrentlyRunningPositionAnimation = true;

					dirtyTracker.hasPositionChanged = true;
				}

				if(sXPoint != null && sYPoint != null && sZPoint != null)
				{
					bone.setScaleX(MathUtil.lerpValues(sXPoint, controller.easingType, controller.customEasingMethod));
					bone.setScaleY(MathUtil.lerpValues(sYPoint, controller.easingType, controller.customEasingMethod));
					bone.setScaleZ(MathUtil.lerpValues(sZPoint, controller.easingType, controller.customEasingMethod));
					snapshot.scaleValueX = bone.getScaleX();
					snapshot.scaleValueY = bone.getScaleY();
					snapshot.scaleValueZ = bone.getScaleZ();
					snapshot.isCurrentlyRunningScaleAnimation = true;

					dirtyTracker.hasScaleChanged = true;
				}
			}
		}

		this.reloadAnimations = false;

		double resetTickLength = manager.getResetSpeed();
		for(Map.Entry<String, DirtyTracker> tracker : modelTracker.entrySet())
		{
			IBone model = tracker.getValue().model;
			BoneSnapshot initialSnapshot = model.getInitialSnapshot();
			BoneSnapshot saveSnapshot = boneSnapshots.get(tracker.getKey()).getRight();
			if(saveSnapshot == null)
			{
				if(crashWhenCantFindBone)
				{
					throw new RuntimeException("Could not find save snapshot for bone: " + tracker.getValue().model.getName() + ". Please don't add bones that are used in an animation at runtime.");
				}
				else
				{
					continue;
				}
			}

			if(!tracker.getValue().hasRotationChanged)
			{
				if(saveSnapshot.isCurrentlyRunningRotationAnimation)
				{
					saveSnapshot.mostRecentResetRotationTick = (float) seekTime;
					saveSnapshot.isCurrentlyRunningRotationAnimation = false;
				}

				double percentageReset = Math.min((seekTime - saveSnapshot.mostRecentResetRotationTick) / resetTickLength, 1);

				model.setRotationX(MathUtil.lerpValues(percentageReset, saveSnapshot.rotationValueX, initialSnapshot.rotationValueX));
				model.setRotationY(MathUtil.lerpValues(percentageReset, saveSnapshot.rotationValueY, initialSnapshot.rotationValueY));
				model.setRotationZ(MathUtil.lerpValues(percentageReset, saveSnapshot.rotationValueZ, initialSnapshot.rotationValueZ));

				if(percentageReset >= 1)
				{
					saveSnapshot.rotationValueX = model.getRotationX();
					saveSnapshot.rotationValueY = model.getRotationY();
					saveSnapshot.rotationValueZ = model.getRotationZ();
				}
			}
			if(!tracker.getValue().hasPositionChanged)
			{
				if(saveSnapshot.isCurrentlyRunningPositionAnimation)
				{
					saveSnapshot.mostRecentResetPositionTick = (float) seekTime;
					saveSnapshot.isCurrentlyRunningPositionAnimation = false;
				}

				double percentageReset = Math.min((seekTime - saveSnapshot.mostRecentResetPositionTick) / resetTickLength, 1);

				model.setPositionX(MathUtil.lerpValues(percentageReset, saveSnapshot.positionOffsetX, initialSnapshot.positionOffsetX));
				model.setPositionY(MathUtil.lerpValues(percentageReset, saveSnapshot.positionOffsetY, initialSnapshot.positionOffsetY));
				model.setPositionZ(MathUtil.lerpValues(percentageReset, saveSnapshot.positionOffsetZ, initialSnapshot.positionOffsetZ));

				if(percentageReset >= 1)
				{
					saveSnapshot.positionOffsetX = model.getPositionX();
					saveSnapshot.positionOffsetY = model.getPositionY();
					saveSnapshot.positionOffsetZ = model.getPositionZ();
				}
			}
			if(!tracker.getValue().hasScaleChanged)
			{
				if(saveSnapshot.isCurrentlyRunningScaleAnimation)
				{
					saveSnapshot.mostRecentResetScaleTick = (float) seekTime;
					saveSnapshot.isCurrentlyRunningScaleAnimation = false;
				}

				double percentageReset = Math.min((seekTime - saveSnapshot.mostRecentResetScaleTick) / resetTickLength, 1);

				model.setScaleX(MathUtil.lerpValues(percentageReset, saveSnapshot.scaleValueX, initialSnapshot.scaleValueX));
				model.setScaleY(MathUtil.lerpValues(percentageReset, saveSnapshot.scaleValueY, initialSnapshot.scaleValueY));
				model.setScaleZ(MathUtil.lerpValues(percentageReset, saveSnapshot.scaleValueZ, initialSnapshot.scaleValueZ));

				if(percentageReset >= 1)
				{
					saveSnapshot.scaleValueX = model.getScaleX();
					saveSnapshot.scaleValueY = model.getScaleY();
					saveSnapshot.scaleValueZ = model.getScaleZ();
				}
			}
		}
		manager.isFirstTick = false;
	}

	private HashMap<String, DirtyTracker> createNewDirtyTracker()
	{
		HashMap<String, DirtyTracker> tracker = new HashMap<>();
		for(IBone bone : modelRendererList)
		{
			tracker.put(bone.getName(), new DirtyTracker(false, false, false, bone));
		}
		return tracker;
	}

	private void updateBoneSnapshots(HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection)
	{
		for(IBone bone : modelRendererList)
		{
			if(!boneSnapshotCollection.containsKey(bone.getName()))
			{
				boneSnapshotCollection.put(bone.getName(), Pair.of(bone, new BoneSnapshot(bone.getInitialSnapshot())));
			}
		}
	}

	public IBone getBone(String boneName)
	{
		return modelRendererList.stream().filter(x -> x.getName().equals(boneName)).findFirst().orElse(null);
	}

	public void registerModelRenderer(IBone modelRenderer)
	{
		modelRenderer.saveInitialSnapshot();
		modelRendererList.add(modelRenderer);
	}


	public void clearModelRendererList()
	{
		this.modelRendererList.clear();
	}

	public List<IBone> getModelRendererList()
	{
		return modelRendererList;
	}

	public void preAnimationSetup(ITyrannomatable animatable, double seekTime)
	{
		this.animatedModel.setMolangQueries(animatable, seekTime);
	}
}