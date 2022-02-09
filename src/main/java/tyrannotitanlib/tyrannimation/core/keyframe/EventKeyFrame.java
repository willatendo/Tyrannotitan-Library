package tyrannotitanlib.tyrannimation.core.keyframe;

public class EventKeyFrame<T> {
	private T eventData;
	public boolean hasExecuted = false;
	private Double startTick;

	public EventKeyFrame(Double startTick, T eventData) {
		this.startTick = startTick;
		this.eventData = eventData;
	}

	public T getEventData() {
		return this.eventData;
	}

	public Double getStartTick() {
		return this.startTick;
	}
}
