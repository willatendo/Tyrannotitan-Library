package tyrannotitanlib.content;

import net.minecraftforge.fml.common.Mod;
import tyrannotitanlib.content.server.util.TyrannoRegistries;
import tyrannotitanlib.library.base.util.TyrannoUtils;

@Mod(TyrannoUtils.TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public TyrannotitanLibrary() 
	{
		TyrannoRegistries.register();
	}
}
