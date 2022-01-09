package tyrannotitanlib.library.tyrannomation.core.event.predicate;

import java.util.List;
import java.util.stream.Collectors;

import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;

public class TyrannomationEvent<T extends ITyrannomatable> {
	private final T animatable;
	public double animationTick;
	private final float limbSwing;
	private final float limbSwingAmount;
	private final float partialTick;
	private final boolean isMoving;
	private final List<Object> extraData;
	protected TyrannomationController controller;

	public TyrannomationEvent(T animatable, float limbSwing, float limbSwingAmount, float partialTick, boolean isMoving, List<Object> extraData) {
		this.animatable = animatable;
		this.limbSwing = limbSwing;
		this.limbSwingAmount = limbSwingAmount;
		this.partialTick = partialTick;
		this.isMoving = isMoving;
		this.extraData = extraData;
	}

	public double getAnimationTick() {
		return animationTick;
	}

	public T getAnimatable() {
		return animatable;
	}

	public float getLimbSwing() {
		return limbSwing;
	}

	public float getLimbSwingAmount() {
		return limbSwingAmount;
	}

	public float getPartialTick() {
		return partialTick;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public TyrannomationController getController() {
		return controller;
	}

	public void setController(TyrannomationController controller) {
		this.controller = controller;
	}

	public List<Object> getExtraData() {
		return extraData;
	}

	public List<T> getExtraDataOfType(Class<T> type) {
		return extraData.stream().filter(x -> type.isAssignableFrom(x.getClass())).map(x -> type.cast(x)).collect(Collectors.toList());
	}
}
