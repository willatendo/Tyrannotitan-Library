package tyrannotitanlib.library.tyrannomation.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class ResourceListener 
{
	public static void registerReloadListener() 
	{
		if(Minecraft.getInstance() != null) 
		{
			if(Minecraft.getInstance().getResourceManager() == null) 
			{
				throw new RuntimeException("TyrannotitanLib was initialized too early!");
			}
			ReloadableResourceManager reloadable = (ReloadableResourceManager) Minecraft.getInstance().getResourceManager();
			reloadable.registerReloadListener(TyrannomationCache.getInstance()::reload);
		} 
		else
		{
			TyrannoUtils.LOGGER.warn("Minecraft.getInstance() was null, could not register reload listeners. Ignore if datagenning.");
		}
	}
}
