package tyrannotitanlib.library.tyrannomationcore;

import tyrannotitanlib.library.tyrannomationcore.manager.TyrannomationData;
import tyrannotitanlib.library.tyrannomationcore.manager.TyrannomationFactory;

public interface ITyrannomatable {
	void registerControllers(TyrannomationData data);

	TyrannomationFactory getFactory();
}
