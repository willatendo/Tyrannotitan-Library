package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.Util.LOG;

import java.util.Locale;

import net.minecraft.world.level.block.entity.BannerPattern;

public class TyrannoBanners {
	public static final BannerPattern TYRANNOTITAN = registerPattern("tyrannotitan");

	public static BannerPattern registerPattern(String id) {
		return BannerPattern.create(id.toUpperCase(Locale.ROOT), id, id, false);
	}

	public static void init() {
		LOG.debug("Registering Tyranno Banners");
	}
}
