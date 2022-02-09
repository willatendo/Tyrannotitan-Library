package tyrannotitanlib.tyrannimation.resource;

import static tyrannotitanlib.core.content.Util.LOG;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;

public class ResourceListener {
	public static void registerReloadListener() {
		if (Minecraft.getInstance() != null) {
			if (Minecraft.getInstance().getResourceManager() == null) {
				throw new RuntimeException("TyrannotitanLib was initialized too early!");
			}
			ReloadableResourceManager reloadable = (ReloadableResourceManager) Minecraft.getInstance().getResourceManager();
			reloadable.registerReloadListener(AnimatedCashe.getInstance()::reload);
		} else {
			LOG.warn("Minecraft.getInstance() was null, could not register reload listeners. Ignore if datagenning.");
		}
	}
}
