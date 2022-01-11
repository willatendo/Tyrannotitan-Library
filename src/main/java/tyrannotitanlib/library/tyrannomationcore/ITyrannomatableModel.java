package tyrannotitanlib.library.tyrannomationcore;

import tyrannotitanlib.library.tyrannomationcore.builder.Tyrannomation;
import tyrannotitanlib.library.tyrannomationcore.event.predicate.TyrannomationEvent;
import tyrannotitanlib.library.tyrannomationcore.processor.IBone;
import tyrannotitanlib.library.tyrannomationcore.processor.TyrannomationProcessor;

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
