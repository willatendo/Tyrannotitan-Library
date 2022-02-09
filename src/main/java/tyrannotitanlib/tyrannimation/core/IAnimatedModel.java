package tyrannotitanlib.tyrannimation.core;

import tyrannotitanlib.tyrannimation.core.builder.Animation;
import tyrannotitanlib.tyrannimation.core.event.predicate.AnimationEvent;
import tyrannotitanlib.tyrannimation.core.processor.AnimatedProcessor;
import tyrannotitanlib.tyrannimation.core.processor.IAnimatedBone;

public interface IAnimatedModel<E> {
	default double getCurrentTick() {
		return (System.nanoTime() / 1000000L / 50.0);
	}

	default void setLivingAnimations(E entity, Integer uniqueID) {
		this.setLivingAnimations(entity, uniqueID, null);
	}

	void setLivingAnimations(E entity, Integer uniqueID, AnimationEvent customPredicate);

	AnimatedProcessor getAnimationProcessor();

	Animation getAnimation(String name, IAnimated animatable);

	default IAnimatedBone getBone(String boneName) {
		IAnimatedBone bone = this.getAnimationProcessor().getBone(boneName);
		if (bone == null) {
			throw new RuntimeException("Could not find bone: " + boneName);
		}
		return bone;
	}

	void setMolangQueries(IAnimated animatable, double currentTick);
}
