package tyrannotitanlib.library.tyrannomation.core.event;

import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;

public class ParticleKeyFrameEvent<T> extends KeyframeEvent<T> {
	public final String effect;
	public final String locator;
	public final String script;

	public ParticleKeyFrameEvent(T entity, double animationTick, String effect, String locator, String script, TyrannomationController controller) {
		super(entity, animationTick, controller);
		this.effect = effect;
		this.locator = locator;
		this.script = script;
	}
}
