package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.Util.LOG;

import net.minecraft.world.level.block.entity.BannerPattern;
import tyrannotitanlib.tyranniregister.TyrannoRegister;

public class TyrannoBanners {
	public static final BannerPattern TYRANNOTITAN = TyrannoRegister.registerPattern("tyrannotitan");

	public static void init() {
		LOG.debug("Registering Tyranno Banners");
	}
}
