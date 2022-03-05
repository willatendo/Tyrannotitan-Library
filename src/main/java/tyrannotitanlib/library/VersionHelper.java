package tyrannotitanlib.library;

import static tyrannotitanlib.core.content.Util.TYRANNO_UTILS;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

public enum VersionHelper {
	DEV,
	SNAPSHOT,
	YOUTUBE,
	PUBLIC;

	public static MutableComponent getMessage(VersionHelper version, String versionString) {
		if (version == DEV) {
			return TYRANNO_UTILS.idBoundArgsFormatedText("event", "load_dev_build", versionString, ChatFormatting.DARK_RED, ChatFormatting.BOLD);
		} else if (version == SNAPSHOT) {
			return TYRANNO_UTILS.idBoundArgsFormatedText("event", "load_snapshot_build", versionString, ChatFormatting.AQUA);
		} else if (version == YOUTUBE) {
			return TYRANNO_UTILS.idBoundArgsFormatedText("event", "load_youtube_build", versionString, ChatFormatting.RED);
		} else {
			return TYRANNO_UTILS.idBoundArgsFormatedText("event", "load", versionString, ChatFormatting.GOLD, ChatFormatting.ITALIC);
		}
	}

	public static VersionHelper getVersion(String version) {
		if (version.contains("dev")) {
			return DEV;
		} else if (version.contains("snapshot")) {
			return SNAPSHOT;
		} else {
			return PUBLIC;
		}
	}

	public static String toStringVersion(String version) {
		return version.replace("_", "").replace("dev", "").replace("a", "Alpha ").replace("b", "Beta ");
	}
}
