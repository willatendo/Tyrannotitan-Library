package core.content.init;

import static core.content.Util.LOG;
import net.minecraft.world.level.block.entity.BannerPattern;

public class TyrannoBanners {
	public static final BannerPattern TYRANNOTITAN = TyrannoRegistries.create("tyrannotitan");

	public static void init() {
		LOG.debug("Registering Tyranno Banners");
	}
}
