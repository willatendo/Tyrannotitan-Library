package tyrannotitanlib.library.tyrannomation.core;

import tyrannotitanlib.library.tyrannomation.core.builder.Tyrannomation;
import tyrannotitanlib.library.tyrannomation.core.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomation.core.processor.TyrannomationProcessor;
import tyrannotitanlib.library.tyrannomation.core.processor.IBone;

public interface ITyrannomatableModel<E> {
	default double getCurrentTick() {
		return (System.nanoTime() / 1000000L / 50.0);
	}

	default void setLivingAnimations(E entity, Integer uniqueID) {
		this.setLivingAnimations(entity, uniqueID, null);
	}

	void setLivingAnimations(E entity, Integer uniqueID, TyrannomationEvent customPredicate);

	TyrannomationProcessor getAnimationProcessor();

	Tyrannomation getAnimation(String name, ITyrannomatable animatable);

	default IBone getBone(String boneName) {
		IBone bone = this.getAnimationProcessor().getBone(boneName);
		if (bone == null) {
			throw new RuntimeException("Could not find bone: " + boneName);
		}
		return bone;
	}

	void setMolangQueries(ITyrannomatable animatable, double currentTick);
}
