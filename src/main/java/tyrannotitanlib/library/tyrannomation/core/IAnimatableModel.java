package tyrannotitanlib.library.tyrannomation.core;

import tyrannotitanlib.library.tyrannomation.core.builder.Animation;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.AnimationEvent;
import tyrannotitanlib.library.tyrannomation.core.processor.AnimationProcessor;
import tyrannotitanlib.library.tyrannomation.core.processor.IBone;

public interface IAnimatableModel<E>
{
	default double getCurrentTick()	
	{
		return (System.nanoTime() / 1000000L / 50.0);
	}

	default void setLivingAnimations(E entity, Integer uniqueID)
	{
		this.setLivingAnimations(entity, uniqueID, null);
	}

	void setLivingAnimations(E entity, Integer uniqueID, AnimationEvent customPredicate);

	AnimationProcessor getAnimationProcessor();

	Animation getAnimation(String name, IAnimatable animatable);

	default IBone getBone(String boneName)
	{
		IBone bone = this.getAnimationProcessor().getBone(boneName);
		if(bone == null)
		{
			throw new RuntimeException("Could not find bone: " + boneName);
		}
		return bone;
	}

	void setMolangQueries(IAnimatable animatable, double currentTick);
}
