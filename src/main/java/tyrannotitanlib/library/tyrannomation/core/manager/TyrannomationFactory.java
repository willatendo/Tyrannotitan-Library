package tyrannotitanlib.library.tyrannomation.core.manager;

import java.util.HashMap;

import tyrannotitanlib.library.tyrannomation.core.ITyrannomatable;

public class TyrannomationFactory {
	private final ITyrannomatable animatable;
	private HashMap<Integer, TyrannomationData> animationDataMap = new HashMap<>();

	public TyrannomationFactory(ITyrannomatable animatable) {
		this.animatable = animatable;
	}

	public TyrannomationData getOrCreateAnimationData(Integer uniqueID) {
		if (!this.animationDataMap.containsKey(uniqueID)) {
			TyrannomationData data = new TyrannomationData();
			this.animatable.registerControllers(data);
			this.animationDataMap.put(uniqueID, data);
		}
		return animationDataMap.get(uniqueID);
	}
}
