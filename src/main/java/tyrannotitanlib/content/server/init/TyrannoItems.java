package tyrannotitanlib.content.server.init;

import net.minecraft.item.Item;
import tyrannotitanlib.library.base.item.TyrannoDevKit;
import tyrannotitanlib.library.base.util.TyrannoUtils;

public class TyrannoItems 
{	
	public static final Item DEV_KIT = TyrannoDevKit.init();
	
	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Items"); }
}
