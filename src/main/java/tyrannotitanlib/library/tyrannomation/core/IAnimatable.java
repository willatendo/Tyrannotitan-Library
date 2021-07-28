package tyrannotitanlib.library.tyrannomation.core;

import tyrannotitanlib.library.tyrannomation.core.manager.AnimationData;
import tyrannotitanlib.library.tyrannomation.core.manager.AnimationFactory;

public interface IAnimatable
{
	void registerControllers(AnimationData data);
	AnimationFactory getFactory();
}
