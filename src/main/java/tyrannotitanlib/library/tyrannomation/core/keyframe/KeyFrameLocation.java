package tyrannotitanlib.library.tyrannomation.core.keyframe;

public class KeyFrameLocation<T extends KeyFrame> {
	public T currentFrame;

	public double currentTick;

	public KeyFrameLocation(T currentFrame, double currentTick) {
		this.currentFrame = currentFrame;
		this.currentTick = currentTick;
	}
}
