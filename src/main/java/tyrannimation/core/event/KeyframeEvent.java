package tyrannimation.core.event;

import tyrannimation.core.controller.TyrannomationController;

public abstract class KeyframeEvent<T> {
	private final T entity;
	private final double animationTick;
	private final TyrannomationController controller;

	public KeyframeEvent(T entity, double animationTick, TyrannomationController controller) {
		this.entity = entity;
		this.animationTick = animationTick;
		this.controller = controller;
	}

	public double getAnimationTick() {
		return animationTick;
	}

	public T getEntity() {
		return entity;
	}

	public TyrannomationController getController() {
		return controller;
	}
}
