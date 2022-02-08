package tyrannimation.core;

import tyrannimation.core.builder.Tyrannomation;
import tyrannimation.core.event.predicate.TyrannomationEvent;
import tyrannimation.core.processor.AnimatedProcessor;
import tyrannimation.core.processor.IAnimatedBone;

public interface IAnimatedModel<E> {
	default double getCurrentTick() {
		return (System.nanoTime() / 1000000L / 50.0);
	}

	default void setLivingAnimations(E entity, Integer uniqueID) {
		this.setLivingAnimations(entity, uniqueID, null);
	}

	void setLivingAnimations(E entity, Integer uniqueID, TyrannomationEvent customPredicate);

	AnimatedProcessor getAnimationProcessor();

	Tyrannomation getAnimation(String name, IAnimated animatable);

	default IAnimatedBone getBone(String boneName) {
		IAnimatedBone bone = this.getAnimationProcessor().getBone(boneName);
		if (bone == null) {
			throw new RuntimeException("Could not find bone: " + boneName);
		}
		return bone;
	}

	void setMolangQueries(IAnimated animatable, double currentTick);
}
