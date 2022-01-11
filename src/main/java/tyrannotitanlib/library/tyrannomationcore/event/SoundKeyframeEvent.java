package tyrannotitanlib.library.tyrannomationcore.event;

import tyrannotitanlib.library.tyrannomationcore.controller.TyrannomationController;

public class SoundKeyframeEvent<T> extends KeyframeEvent<T> {
	public final String sound;

	public SoundKeyframeEvent(T entity, double animationTick, String sound, TyrannomationController controller) {
		super(entity, animationTick, controller);
		this.sound = sound;
	}
}
