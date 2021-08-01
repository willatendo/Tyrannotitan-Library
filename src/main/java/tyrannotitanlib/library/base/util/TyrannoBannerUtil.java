package tyrannotitanlib.library.base.util;

import java.util.Locale;

import net.minecraft.tileentity.BannerPattern;

/*
 * Creates a new banner pattern. (@TeamAbnormals)
 */

public class TyrannoBannerUtil 
{
	public static BannerPattern createPattern(String name, String id) 
	{
		return BannerPattern.create(name.toUpperCase(Locale.ROOT), name, id, false);
	}
}
