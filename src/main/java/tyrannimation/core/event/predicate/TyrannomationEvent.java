package tyrannimation.core.event.predicate;

import java.util.List;
import java.util.stream.Collectors;

import tyrannimation.core.IAnimated;
import tyrannimation.core.controller.TyrannomationController;

public class TyrannomationEvent<T extends IAnimated> {
	private final T animated;
	public double animatedTick;
	private final float limbSwing;
	private final float limbSwingAmount;
	private final float partialTick;
	private final boolean isMoving;
	private final List<Object> extraData;
	protected TyrannomationController controller;

	public TyrannomationEvent(T animatable, float limbSwing, float limbSwingAmount, float partialTick, boolean isMoving, List<Object> extraData) {
		this.animated = animatable;
		this.limbSwing = limbSwing;
		this.limbSwingAmount = limbSwingAmount;
		this.partialTick = partialTick;
		this.isMoving = isMoving;
		this.extraData = extraData;
	}

	public double getAnimatedTick() {
		return this.animatedTick;
	}

	public T getAnimated() {
		return this.animated;
	}

	public float getLimbSwing() {
		return this.limbSwing;
	}

	public float getLimbSwingAmount() {
		return this.limbSwingAmount;
	}

	public float getPartialTick() {
		return this.partialTick;
	}

	public boolean isMoving() {
		return this.isMoving;
	}

	public TyrannomationController getController() {
		return this.controller;
	}

	public void setController(TyrannomationController controller) {
		this.controller = controller;
	}

	public List<Object> getExtraData() {
		return this.extraData;
	}

	public List<T> getExtraDataOfType(Class<T> type) {
		return this.extraData.stream().filter(x -> type.isAssignableFrom(x.getClass())).map(x -> type.cast(x)).collect(Collectors.toList());
	}
}
