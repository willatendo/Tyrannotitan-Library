package tyrannimation.core;

import tyrannimation.core.manager.AnimatedData;
import tyrannimation.core.manager.AnimatedFactory;

public interface IAnimated {
	void registerAnimationControl(AnimatedData data);

	AnimatedFactory getAnimationFactory();
}
