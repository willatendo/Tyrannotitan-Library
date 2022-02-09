package tyrannotitanlib.library.compatibility;

import net.minecraftforge.fml.ModList;

public class CompatibilitiesUtils {
	public static final String QUARK_ID = "quark";
	public static final String DECORATIVE_BLOCKS_ID = "decorative_blocks";

	public static boolean isQuarkLoaded() {
		return ModList.get().isLoaded(QUARK_ID);
	}

	public static boolean isDecorativeBlocksLoaded() {
		return ModList.get().isLoaded(DECORATIVE_BLOCKS_ID);
	}
}
