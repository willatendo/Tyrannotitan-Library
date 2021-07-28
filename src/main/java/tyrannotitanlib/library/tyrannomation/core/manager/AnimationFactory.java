package tyrannotitanlib.library.tyrannomation.core.manager;

import java.util.HashMap;

import tyrannotitanlib.library.tyrannomation.core.IAnimatable;

public class AnimationFactory
{
	private final IAnimatable animatable;
	private HashMap<Integer, AnimationData> animationDataMap = new HashMap<>();

	public AnimationFactory(IAnimatable animatable)
	{
		this.animatable = animatable;
	}

	public AnimationData getOrCreateAnimationData(Integer uniqueID)
	{
		if (!animationDataMap.containsKey(uniqueID))
		{
			AnimationData data = new AnimationData();
			animatable.registerControllers(data);
			animationDataMap.put(uniqueID, data);
		}
		return animationDataMap.get(uniqueID);
	}
}
