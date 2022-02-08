package tyrannimation.core.keyframe;

import com.eliotlash.mclib.math.IValue;

public class AnimatedBone {
	public String boneName;
	public VectorKeyFrameList<KeyFrame<IValue>> rotationKeyFrames;
	public VectorKeyFrameList<KeyFrame<IValue>> positionKeyFrames;
	public VectorKeyFrameList<KeyFrame<IValue>> scaleKeyFrames;
}
