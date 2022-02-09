package tyrannotitanlib.tyrannimation.core.event;

import tyrannotitanlib.tyrannimation.core.controller.AnimationController;

public class SoundKeyframeEvent<T> extends KeyframeEvent<T> {
	public final String sound;

	public SoundKeyframeEvent(T entity, double animatedTick, String sound, AnimationController controller) {
		super(entity, animatedTick, controller);
		this.sound = sound;
	}
}
