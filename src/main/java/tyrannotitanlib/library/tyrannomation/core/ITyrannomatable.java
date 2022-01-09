package tyrannotitanlib.library.tyrannomation.core;

import tyrannotitanlib.library.tyrannomation.core.manager.TyrannomationData;
import tyrannotitanlib.library.tyrannomation.core.manager.TyrannomationFactory;

public interface ITyrannomatable {
	void registerControllers(TyrannomationData data);

	TyrannomationFactory getFactory();
}
