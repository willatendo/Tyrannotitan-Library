package tyrannotitanlib.tyrannimation.core;

import tyrannotitanlib.tyrannimation.core.manager.AnimatedData;
import tyrannotitanlib.tyrannimation.core.manager.AnimatedFactory;

public interface IAnimated {
	void registerAnimationControl(AnimatedData data);

	AnimatedFactory getAnimationFactory();
}
