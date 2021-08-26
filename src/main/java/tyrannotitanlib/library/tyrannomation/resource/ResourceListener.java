package tyrannotitanlib.library.tyrannomation.resource;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import tyrannotitanlib.library.base.util.TyrannoUtils;

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
			IReloadableResourceManager reloadable = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
			reloadable.registerReloadListener(TyrannomationCache.getInstance()::reload);
		} 
		else
		{
			TyrannoUtils.LOGGER.warn("Minecraft.getInstance() was null, could not register reload listeners. Ignore if datagenning.");
		}
	}
}
