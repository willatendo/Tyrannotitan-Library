package tyrannimation.core.event;

import tyrannimation.core.controller.TyrannomationController;

public class SoundKeyframeEvent<T> extends KeyframeEvent<T> {
	public final String sound;

	public SoundKeyframeEvent(T entity, double animatedTick, String sound, TyrannomationController controller) {
		super(entity, animatedTick, controller);
		this.sound = sound;
	}
}
