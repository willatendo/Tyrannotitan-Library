package tyrannotitanlib.tyrannimation.core.event.predicate;

import java.util.List;
import java.util.stream.Collectors;

import tyrannotitanlib.tyrannimation.core.IAnimated;
import tyrannotitanlib.tyrannimation.core.controller.AnimationController;

public class AnimationEvent<T extends IAnimated> {
	private final T animated;
	public double animatedTick;
	private final float limbSwing;
	private final float limbSwingAmount;
	private final float partialTick;
	private final boolean isMoving;
	private final List<Object> extraData;
	protected AnimationController controller;

	public AnimationEvent(T animatable, float limbSwing, float limbSwingAmount, float partialTick, boolean isMoving, List<Object> extraData) {
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

	public AnimationController getController() {
		return this.controller;
	}

	public void setController(AnimationController controller) {
		this.controller = controller;
	}

	public List<Object> getExtraData() {
		return this.extraData;
	}

	public <T> List<T> getExtraDataOfType(Class<T> type) {
		return extraData.stream().filter(x -> type.isAssignableFrom(x.getClass())).map(x -> type.cast(x)).collect(Collectors.toList());
	}
}
