package tyrannotitanlib.core.content;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class Util {
	public static final String TYRANNO_ID = "tyrannotitanlib";
	public static final UtilitiesRegistry TYRANNO_UTILS = new UtilitiesRegistry(TYRANNO_ID);
	public static final Logger LOG = TYRANNO_UTILS.getLogger();

	// Official Mod Utilities
	public static final String LOST_WORLDS_ID = "lostworlds";
	public static final UtilitiesRegistry LOST_WORLDS_UTILS = new UtilitiesRegistry(LOST_WORLDS_ID);
	public static final Logger LW_LOG = LOST_WORLDS_UTILS.getLogger();

	public static final String HYLANDA_ID = "hylanda";
	public static final UtilitiesRegistry HYLANDA_UTILS = new UtilitiesRegistry(HYLANDA_ID);
	public static final Logger HYLANDA_LOG = HYLANDA_UTILS.getLogger();

	public static final String ROSES_ID = "roses";
	public static final UtilitiesRegistry ROSES_UTILS = new UtilitiesRegistry(ROSES_ID);
	public static final Logger ROSES_LOG = ROSES_UTILS.getLogger();

	// Common Utilities
	@Nullable
	public static BufferedReader getURLContents(@Nonnull String urlString, @Nonnull String backupFileLoc) {
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			InputStream stream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(stream);

			return new BufferedReader(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			return new BufferedReader(new InputStreamReader(Util.class.getClass().getClassLoader().getResourceAsStream(backupFileLoc), StandardCharsets.UTF_8));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Block[] collectBlocks(Class<?> blockClass) {
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
}
