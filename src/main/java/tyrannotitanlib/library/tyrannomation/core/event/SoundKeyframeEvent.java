package tyrannotitanlib.library.tyrannomation.core.event;

import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;

public class SoundKeyframeEvent<T> extends KeyframeEvent<T> {
	public final String sound;

	public SoundKeyframeEvent(T entity, double animationTick, String sound, TyrannomationController controller) {
		super(entity, animationTick, controller);
		this.sound = sound;
	}
}
