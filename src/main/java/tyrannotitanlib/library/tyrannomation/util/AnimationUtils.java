package tyrannotitanlib.library.tyrannomation.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.renderers.tyranno.ITyrannomationRenderer;

public class AnimationUtils 
{
	public static double convertTicksToSeconds(double ticks) 
	{
		return ticks / 20;
	}

	public static double convertSecondsToTicks(double seconds) 
	{
		return seconds * 20;
	}

	public static <T extends Entity> EntityRenderer<T> getRenderer(T entity) 
	{
		EntityRendererManager renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
		return (EntityRenderer<T>) renderManager.getRenderer(entity);
	}

	public static <T extends Entity> TyrannomationModelProvider getGeoModelForEntity(T entity) 
	{
		EntityRenderer<T> entityRenderer = getRenderer(entity);

		if(entityRenderer instanceof ITyrannomationRenderer) 
		{
			return ((ITyrannomationRenderer<?>) entityRenderer).getGeoModelProvider();
		}
		return null;
	}
}
