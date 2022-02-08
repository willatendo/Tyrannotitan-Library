package tyrannimation.core.manager;

import java.util.HashMap;

import tyrannimation.core.IAnimated;

public class AnimatedFactory {
	private final IAnimated animated;
	private HashMap<Integer, AnimatedData> animatedDataMap = new HashMap<>();

	public AnimatedFactory(IAnimated animated) {
		this.animated = animated;
	}

	public AnimatedData getOrCreateAnimationData(Integer uniqueID) {
		if (!this.animatedDataMap.containsKey(uniqueID)) {
			AnimatedData data = new AnimatedData();
			this.animated.registerAnimationControl(data);
			this.animatedDataMap.put(uniqueID, data);
		}
		return animatedDataMap.get(uniqueID);
	}
}
