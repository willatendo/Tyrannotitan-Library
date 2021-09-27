package tyrannotitanlib.content.server.init;

import tyrannotitanlib.library.base.item.TyrannoDevKit;
import tyrannotitanlib.library.base.utils.TyrannoUtils;

public class TyrannoItems 
{		
	public static void init() 
	{
		TyrannoUtils.LOGGER.debug("Registering Tyranno Items"); 
		
		TyrannoDevKit.init();
	}
}
