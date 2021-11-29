package tyrannotitanlib.content.server.init;

import net.minecraft.tileentity.BannerPattern;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoBanners 
{
	public static final BannerPattern TYRANNOTITAN = TyrannoRegistries.create("tyrannotitan");
	
	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Banners"); }
}
