package tyrannotitanlib.library.tyrannomation.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.eliotlash.mclib.math.IValue;
import com.eliotlash.molang.MolangParser;

import tyrannotitanlib.library.tyrannomation.core.TyrannomationState;
import tyrannotitanlib.library.tyrannomation.core.ConstantValue;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.ITyrannomatableModel;
import tyrannotitanlib.library.tyrannomation.core.PlayState;
import tyrannotitanlib.library.tyrannomation.core.builder.Tyrannomation;
import tyrannotitanlib.library.tyrannomation.core.builder.TyrannomationBuilder;
import tyrannotitanlib.library.tyrannomation.core.easing.EasingType;
import tyrannotitanlib.library.tyrannomation.core.event.CustomInstructionKeyframeEvent;
import tyrannotitanlib.library.tyrannomation.core.event.ParticleKeyFrameEvent;
import tyrannotitanlib.library.tyrannomation.core.event.SoundKeyframeEvent;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.core.keyframe.TyrannomationPoint;
import tyrannotitanlib.library.tyrannomation.core.keyframe.BoneTyrannomation;
import tyrannotitanlib.library.tyrannomation.core.keyframe.BoneTyrannomationQueue;
import tyrannotitanlib.library.tyrannomation.core.keyframe.EventKeyFrame;
import tyrannotitanlib.library.tyrannomation.core.keyframe.KeyFrame;
import tyrannotitanlib.library.tyrannomation.core.keyframe.KeyFrameLocation;
import tyrannotitanlib.library.tyrannomation.core.keyframe.ParticleEventKeyFrame;
import tyrannotitanlib.library.tyrannomation.core.keyframe.VectorKeyFrameList;
import tyrannotitanlib.library.tyrannomation.core.processor.IBone;
import tyrannotitanlib.library.tyrannomation.core.snapshot.BoneSnapshot;
import tyrannotitanlib.library.tyrannomation.core.util.Axis;

public class TyrannomationController<T extends ITyrannomatable>
{
	static List<Function<ITyrannomatable, ITyrannomatableModel>> modelFetchers = new ArrayList<>();

	protected T animatable;

	protected IAnimationPredicate<T> animationPredicate;

	private String name;

	protected TyrannomationState animationState = TyrannomationState.STOPPED;

	public double transitionLengthTicks;

	private ISoundListener soundListener;

	private IParticleListener particleListener;

	private ICustomInstructionListener customInstructionListener;

	public boolean isJustStarting = false;

	public static void addModelFetcher(Function<ITyrannomatable, ITyrannomatableModel> fetcher)
	{
		modelFetchers.add(fetcher);
	}

	@FunctionalInterface
	public interface IAnimationPredicate<P extends ITyrannomatable>
	{
		PlayState test(TyrannomationEvent<P> event);
	}

	@FunctionalInterface
	public interface ISoundListener
	{
		<A extends ITyrannomatable> void playSound(SoundKeyframeEvent<A> event);
	}

	@FunctionalInterface
	public interface IParticleListener
	{
		<A extends ITyrannomatable> void summonParticle(ParticleKeyFrameEvent<A> event);
	}

	@FunctionalInterface
	public interface ICustomInstructionListener
	{
		<A extends ITyrannomatable> void executeInstruction(CustomInstructionKeyframeEvent<A> event);
	}

	private final HashMap<String, BoneTyrannomationQueue> boneAnimationQueues = new HashMap<>();
	private double tickOffset = 0;
	protected Queue<Tyrannomation> animationQueue = new LinkedList<>();
	protected Tyrannomation currentAnimation;
	protected TyrannomationBuilder currentAnimationBuilder = new TyrannomationBuilder();
	protected boolean shouldResetTick = false;
	private HashMap<String, BoneSnapshot> boneSnapshots = new HashMap<>();
	private boolean justStopped = false;
	protected boolean justStartedTransition = false;
	public Function<Double, Double> customEasingMethod;
	protected boolean needsAnimationReload = false;

	public void setAnimation(TyrannomationBuilder builder)
	{
		ITyrannomatableModel model = getModel(this.animatable);
		if(model != null)
		{
			if(builder == null || builder.getRawAnimationList().size() == 0)
			{
				animationState = TyrannomationState.STOPPED;
			}
			else if(!builder.getRawAnimationList().equals(currentAnimationBuilder.getRawAnimationList()) || needsAnimationReload)
			{
				AtomicBoolean encounteredError = new AtomicBoolean(false);
				ITyrannomatableModel finalModel = model;
				LinkedList<Tyrannomation> animations = new LinkedList<>(builder.getRawAnimationList().stream().map((rawAnimation) ->
				{
					Tyrannomation animation = finalModel.getAnimation(rawAnimation.animationName, animatable);
					if(animation == null)
					{
						System.out.println("Could not load animation: " + rawAnimation.animationName + ". Is it missing?");
						encounteredError.set(true);
					}
					
					if(animation != null && rawAnimation.loop != null)
					{
						animation.loop = rawAnimation.loop;
					}
					return animation;
				}).collect(Collectors.toList()));

				if(encounteredError.get())
				{
					return;
				}
				else
				{
					animationQueue = animations;
				}
				
				currentAnimationBuilder = builder;

				shouldResetTick = true;
				this.animationState = TyrannomationState.TRANSITIONING;
				justStartedTransition = true;
				needsAnimationReload = false;
			}
		}
	}

	public EasingType easingType = EasingType.NONE;

	public TyrannomationController(T animatable, String name, float transitionLengthTicks, IAnimationPredicate<T> animationPredicate)
	{
		this.animatable = animatable;
		this.name = name;
		this.transitionLengthTicks = transitionLengthTicks;
		this.animationPredicate = animationPredicate;
	}

	public TyrannomationController(T animatable, String name, float transitionLengthTicks, EasingType easingtype, IAnimationPredicate<T> animationPredicate)
	{
		this.animatable = animatable;
		this.name = name;
		this.transitionLengthTicks = transitionLengthTicks;
		this.easingType = easingtype;
		this.animationPredicate = animationPredicate;
	}

	public TyrannomationController(T animatable, String name, float transitionLengthTicks, Function<Double, Double> customEasingMethod, IAnimationPredicate<T> animationPredicate)
	{
		this.animatable = animatable;
		this.name = name;
		this.transitionLengthTicks = transitionLengthTicks;
		this.customEasingMethod = customEasingMethod;
		this.easingType = EasingType.CUSTOM;
		this.animationPredicate = animationPredicate;
	}

	public String getName()
	{
		return name;
	}

	public Tyrannomation getCurrentAnimation()
	{
		return currentAnimation;
	}

	public TyrannomationState getAnimationState()
	{
		return animationState;
	}

	public HashMap<String, BoneTyrannomationQueue> getBoneAnimationQueues()
	{
		return boneAnimationQueues;
	}

	public void registerSoundListener(ISoundListener soundListener)
	{
		this.soundListener = soundListener;
	}

	public void registerParticleListener(IParticleListener particleListener)
	{
		this.particleListener = particleListener;
	}

	public void registerCustomInstructionListener(ICustomInstructionListener customInstructionListener)
	{
		this.customInstructionListener = customInstructionListener;
	}

	public void process(double tick, TyrannomationEvent event, List<IBone> modelRendererList, HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection, MolangParser parser, boolean crashWhenCantFindBone)
	{
		if(currentAnimation != null)
		{
			ITyrannomatableModel model = getModel(this.animatable);
			Tyrannomation animation = model.getAnimation(currentAnimation.animationName, this.animatable);
			if(model != null && animation != null)
			{
				boolean loop = currentAnimation.loop;
				currentAnimation = animation;
				currentAnimation.loop = loop;
			}
		}

		createInitialQueues(modelRendererList);

		double actualTick = tick;
		tick = adjustTick(tick);

		if(animationState == TyrannomationState.TRANSITIONING && tick >= transitionLengthTicks)
		{
			this.shouldResetTick = true;
			animationState = TyrannomationState.RUNNING;
			tick = adjustTick(actualTick);
		}

		assert tick >= 0 : "Tyrannomation: Tick was less than zero";

		PlayState playState = this.testAnimationPredicate(event);
		if(playState == PlayState.STOP || (currentAnimation == null && animationQueue.size() == 0))
		{
			animationState = TyrannomationState.STOPPED;
			justStopped = true;
			return;
		}
		
		if(justStartedTransition && (shouldResetTick || justStopped))
		{
			justStopped = false;
			tick = adjustTick(actualTick);
		}
		else if(currentAnimation == null && this.animationQueue.size() != 0)
		{
			this.shouldResetTick = true;
			this.animationState = TyrannomationState.TRANSITIONING;
			justStartedTransition = true;
			needsAnimationReload = false;
			tick = adjustTick(actualTick);
		}
		else
		{
			if(animationState != TyrannomationState.TRANSITIONING)
			{
				animationState = TyrannomationState.RUNNING;
			}
		}

		if(animationState == TyrannomationState.TRANSITIONING)
		{
			if(tick == 0 || isJustStarting)
			{
				justStartedTransition = false;
				this.currentAnimation = animationQueue.poll();
				resetEventKeyFrames(currentAnimation);
				saveSnapshotsForAnimation(currentAnimation, boneSnapshotCollection);
			}
			
			if(currentAnimation != null)
			{
				setAnimTime(parser, 0);
				for(BoneTyrannomation boneAnimation : currentAnimation.boneAnimations)
				{
					BoneTyrannomationQueue boneAnimationQueue = boneAnimationQueues.get(boneAnimation.boneName);
					BoneSnapshot boneSnapshot = this.boneSnapshots.get(boneAnimation.boneName);
					Optional<IBone> first = modelRendererList.stream().filter(x -> x.getName().equals(boneAnimation.boneName)).findFirst();
					if(!first.isPresent())
					{
						if(crashWhenCantFindBone)
						{
							throw new RuntimeException("Could not find bone: " + boneAnimation.boneName);
						}
						else
						{
							continue;
						}
					}
					BoneSnapshot initialSnapshot = first.get().getInitialSnapshot();
					assert boneSnapshot != null : "Bone snapshot was null";

					VectorKeyFrameList<KeyFrame<IValue>> rotationKeyFrames = boneAnimation.rotationKeyFrames;
					VectorKeyFrameList<KeyFrame<IValue>> positionKeyFrames = boneAnimation.positionKeyFrames;
					VectorKeyFrameList<KeyFrame<IValue>> scaleKeyFrames = boneAnimation.scaleKeyFrames;

					if(!rotationKeyFrames.xKeyFrames.isEmpty())
					{
						TyrannomationPoint xPoint = getAnimationPointAtTick(rotationKeyFrames.xKeyFrames, 0, true, Axis.X);
						TyrannomationPoint yPoint = getAnimationPointAtTick(rotationKeyFrames.yKeyFrames, 0, true, Axis.Y);
						TyrannomationPoint zPoint = getAnimationPointAtTick(rotationKeyFrames.zKeyFrames, 0, true, Axis.Z);
						boneAnimationQueue.rotationXQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.rotationValueX - initialSnapshot.rotationValueX, xPoint.animationStartValue));
						boneAnimationQueue.rotationYQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.rotationValueY - initialSnapshot.rotationValueY, yPoint.animationStartValue));
						boneAnimationQueue.rotationZQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.rotationValueZ - initialSnapshot.rotationValueZ, zPoint.animationStartValue));
					}

					if(!positionKeyFrames.xKeyFrames.isEmpty())
					{
						TyrannomationPoint xPoint = getAnimationPointAtTick(positionKeyFrames.xKeyFrames, 0, true, Axis.X);
						TyrannomationPoint yPoint = getAnimationPointAtTick(positionKeyFrames.yKeyFrames, 0, true, Axis.Y);
						TyrannomationPoint zPoint = getAnimationPointAtTick(positionKeyFrames.zKeyFrames, 0, true, Axis.Z);
						boneAnimationQueue.positionXQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.positionOffsetX, xPoint.animationStartValue));
						boneAnimationQueue.positionYQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.positionOffsetY, yPoint.animationStartValue));
						boneAnimationQueue.positionZQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.positionOffsetZ, zPoint.animationStartValue));
					}

					if(!scaleKeyFrames.xKeyFrames.isEmpty())
					{
						TyrannomationPoint xPoint = getAnimationPointAtTick(scaleKeyFrames.xKeyFrames, 0, true, Axis.X);
						TyrannomationPoint yPoint = getAnimationPointAtTick(scaleKeyFrames.yKeyFrames, 0, true, Axis.Y);
						TyrannomationPoint zPoint = getAnimationPointAtTick(scaleKeyFrames.zKeyFrames, 0, true, Axis.Z);
						boneAnimationQueue.scaleXQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.scaleValueX, xPoint.animationStartValue));
						boneAnimationQueue.scaleYQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.scaleValueY, yPoint.animationStartValue));
						boneAnimationQueue.scaleZQueue.add(new TyrannomationPoint(null, tick, transitionLengthTicks, boneSnapshot.scaleValueZ, zPoint.animationStartValue));
					}
				}
			}
		}
		else if(getAnimationState() == TyrannomationState.RUNNING)
		{
			processCurrentAnimation(tick, actualTick, parser, crashWhenCantFindBone);
		}
	}

	private void setAnimTime(MolangParser parser, double tick)
	{
		parser.setValue("query.anim_time", tick / 20);
		parser.setValue("query.life_time", tick / 20);
	}

	private ITyrannomatableModel getModel(T animatable)
	{
		for(Function<ITyrannomatable, ITyrannomatableModel> modelGetter : modelFetchers)
		{
			ITyrannomatableModel model = modelGetter.apply(animatable);
			if(model != null)
			{
				return model;
			}
		}
		System.out.println(String.format("Could not find suitable model for animatable of type %s. Did you register a Model Fetcher?", animatable.getClass()));
		return null;
	}

	protected PlayState testAnimationPredicate(TyrannomationEvent<T> event)
	{
		return this.animationPredicate.test(event);
	}

	private void saveSnapshotsForAnimation(Tyrannomation animation, HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection)
	{
		for(Pair<IBone, BoneSnapshot> snapshot : boneSnapshotCollection.values())
		{
			if(animation != null && animation.boneAnimations != null)
			{
				if(animation.boneAnimations.stream().anyMatch(x -> x.boneName.equals(snapshot.getLeft().getName())))
				{
					this.boneSnapshots.put(snapshot.getLeft().getName(), new BoneSnapshot(snapshot.getRight()));
				}
			}
		}
	}

	private void processCurrentAnimation(double tick, double actualTick, MolangParser parser, boolean crashWhenCantFindBone)
	{
		assert currentAnimation != null;
		if(tick >= currentAnimation.animationLength)
		{
			resetEventKeyFrames(currentAnimation);
			if(!currentAnimation.loop)
			{
				Tyrannomation peek = animationQueue.peek();
				if(peek == null)
				{
					this.animationState = TyrannomationState.STOPPED;
					return;
				}
				else
				{
					this.animationState = TyrannomationState.TRANSITIONING;
					shouldResetTick = true;
				}
			}
			else
			{
				shouldResetTick = true;
				tick = adjustTick(actualTick);
			}
		}
		setAnimTime(parser, tick);

		List<BoneTyrannomation> boneAnimations = currentAnimation.boneAnimations;
		for (BoneTyrannomation boneAnimation : boneAnimations)
		{
			BoneTyrannomationQueue boneAnimationQueue = boneAnimationQueues.get(boneAnimation.boneName);
			if(boneAnimationQueue == null)
			{
				if(crashWhenCantFindBone)
				{
					throw new RuntimeException("Could not find bone: " + boneAnimation.boneName);
				}
				else
				{
					continue;
				}
			}

			VectorKeyFrameList<KeyFrame<IValue>> rotationKeyFrames = boneAnimation.rotationKeyFrames;
			VectorKeyFrameList<KeyFrame<IValue>> positionKeyFrames = boneAnimation.positionKeyFrames;
			VectorKeyFrameList<KeyFrame<IValue>> scaleKeyFrames = boneAnimation.scaleKeyFrames;

			if(!rotationKeyFrames.xKeyFrames.isEmpty())
			{
				boneAnimationQueue.rotationXQueue.add(getAnimationPointAtTick(rotationKeyFrames.xKeyFrames, tick, true, Axis.X));
				boneAnimationQueue.rotationYQueue.add(getAnimationPointAtTick(rotationKeyFrames.yKeyFrames, tick, true, Axis.Y));
				boneAnimationQueue.rotationZQueue.add(getAnimationPointAtTick(rotationKeyFrames.zKeyFrames, tick, true, Axis.Z));
			}

			if(!positionKeyFrames.xKeyFrames.isEmpty())
			{
				boneAnimationQueue.positionXQueue.add(getAnimationPointAtTick(positionKeyFrames.xKeyFrames, tick, false, Axis.X));
				boneAnimationQueue.positionYQueue.add(getAnimationPointAtTick(positionKeyFrames.yKeyFrames, tick, false, Axis.Y));
				boneAnimationQueue.positionZQueue.add(getAnimationPointAtTick(positionKeyFrames.zKeyFrames, tick, false, Axis.Z));
			}

			if(!scaleKeyFrames.xKeyFrames.isEmpty())
			{
				boneAnimationQueue.scaleXQueue.add(getAnimationPointAtTick(scaleKeyFrames.xKeyFrames, tick, false, Axis.X));
				boneAnimationQueue.scaleYQueue.add(getAnimationPointAtTick(scaleKeyFrames.yKeyFrames, tick, false, Axis.Y));
				boneAnimationQueue.scaleZQueue.add(getAnimationPointAtTick(scaleKeyFrames.zKeyFrames, tick, false, Axis.Z));
			}
		}

		if(soundListener != null || particleListener != null || customInstructionListener != null)
		{
			for(EventKeyFrame<String> soundKeyFrame : currentAnimation.soundKeyFrames)
			{
				if(!soundKeyFrame.hasExecuted && tick >= soundKeyFrame.getStartTick())
				{
					SoundKeyframeEvent event = new SoundKeyframeEvent(this.animatable, tick, soundKeyFrame.getEventData(), this);
					soundListener.playSound(event);
					soundKeyFrame.hasExecuted = true;
				}
			}

			for(ParticleEventKeyFrame particleEventKeyFrame : currentAnimation.particleKeyFrames)
			{
				if(!particleEventKeyFrame.hasExecuted && tick >= particleEventKeyFrame.getStartTick())
				{
					ParticleKeyFrameEvent event = new ParticleKeyFrameEvent(this.animatable, tick, particleEventKeyFrame.effect, particleEventKeyFrame.locator, particleEventKeyFrame.script, this);
					particleListener.summonParticle(event);
					particleEventKeyFrame.hasExecuted = true;
				}
			}

			for(EventKeyFrame<List<String>> customInstructionKeyFrame : currentAnimation.customInstructionKeyframes)
			{
				if(!customInstructionKeyFrame.hasExecuted && tick >= customInstructionKeyFrame.getStartTick())
				{
					CustomInstructionKeyframeEvent event = new CustomInstructionKeyframeEvent(this.animatable, tick, customInstructionKeyFrame.getEventData(), this);
					customInstructionListener.executeInstruction(event);
					customInstructionKeyFrame.hasExecuted = true;
				}
			}
		}

		if(this.transitionLengthTicks == 0 && shouldResetTick && this.animationState == TyrannomationState.TRANSITIONING)
		{
			this.currentAnimation = animationQueue.poll();
		}
	}

	private void createInitialQueues(List<IBone> modelRendererList)
	{
		boneAnimationQueues.clear();
		for(IBone modelRenderer : modelRendererList)
		{
			boneAnimationQueues.put(modelRenderer.getName(), new BoneTyrannomationQueue(modelRenderer));
		}
	}

	private double adjustTick(double tick)
	{
		if(shouldResetTick)
		{
			this.tickOffset = tick;
			shouldResetTick = false;
			return 0;
		}
		return (tick - this.tickOffset < 0 ? 0 : tick - this.tickOffset);
	}

	private TyrannomationPoint getAnimationPointAtTick(List<KeyFrame<IValue>> frames, double tick, boolean isRotation, Axis axis)
	{
		KeyFrameLocation<KeyFrame<IValue>> location = getCurrentKeyFrameLocation(frames, tick);
		KeyFrame<IValue> currentFrame = location.currentFrame;
		double startValue = currentFrame.getStartValue().get();
		double endValue = currentFrame.getEndValue().get();

		if(isRotation)
		{
			if(!(currentFrame.getStartValue() instanceof ConstantValue))
			{
				startValue = Math.toRadians(startValue);
				if(axis == Axis.X || axis == Axis.Y)
				{
					startValue *= -1;
				}
			}
			
			if(!(currentFrame.getEndValue() instanceof ConstantValue))
			{
				endValue = Math.toRadians(endValue);
				if(axis == Axis.X || axis == Axis.Y)
				{
					endValue *= -1;
				}
			}
		}

		return new TyrannomationPoint(currentFrame, location.currentTick, currentFrame.getLength(), startValue, endValue);
	}

	private KeyFrameLocation<KeyFrame<IValue>> getCurrentKeyFrameLocation(List<KeyFrame<IValue>> frames, double ageInTicks)
	{
		double totalTimeTracker = 0;
		for(int i = 0; i < frames.size(); i++)
		{
			KeyFrame frame = frames.get(i);
			totalTimeTracker += frame.getLength();
			if(totalTimeTracker > ageInTicks)
			{
				double tick = (ageInTicks - (totalTimeTracker - frame.getLength()));
				return new KeyFrameLocation<>(frame, tick);
			}
		}
		return new KeyFrameLocation(frames.get(frames.size() - 1), ageInTicks);
	}

	private void resetEventKeyFrames(Tyrannomation animation)
	{
		if(animation == null)
		{
			return;
		}
		if(!animation.soundKeyFrames.isEmpty())
		{
			for (EventKeyFrame soundKeyFrame : animation.soundKeyFrames)
			{
				soundKeyFrame.hasExecuted = false;
			}
		}
		if(!animation.particleKeyFrames.isEmpty())
		{
			for (EventKeyFrame particleKeyFrame : animation.particleKeyFrames)
			{
				particleKeyFrame.hasExecuted = false;
			}
		}
		if(!animation.customInstructionKeyframes.isEmpty())
		{
			for (EventKeyFrame customInstructionKeyFrame : animation.customInstructionKeyframes)
			{
				customInstructionKeyFrame.hasExecuted = false;
			}
		}
	}

	public void markNeedsReload()
	{
		this.needsAnimationReload = true;
	}

	public void clearAnimationCache()
	{
		this.currentAnimationBuilder = new TyrannomationBuilder();
	}
}
