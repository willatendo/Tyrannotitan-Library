package tyrannimation.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import tyrannimation.core.processor.IAnimatedBone;
import tyrannimation.model.provider.AnimatedModelProvider;
import tyrannimation.renderers.IAnimatedRenderer;

public class TyrannomationUtils {
	public static double convertTicksToSeconds(double ticks) {
		return ticks / 20;
	}

	public static double convertSecondsToTicks(double seconds) {
		return seconds * 20;
	}

	public static <T extends Entity> EntityRenderer<T> getRenderer(T entity) {
		EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
		return (EntityRenderer<T>) renderManager.getRenderer(entity);
	}

	public static <T extends Entity> AnimatedModelProvider getTyrannoModelForEntity(T entity) {
		EntityRenderer<T> entityRenderer = getRenderer(entity);

		if (entityRenderer instanceof IAnimatedRenderer) {
			return ((IAnimatedRenderer<?>) entityRenderer).getAnimatedModelProvider();
		}
		return null;
	}

	public static void copyRotations(ModelPart from, IAnimatedBone to) {
		to.setRotationX(-from.xRot);
		to.setRotationY(-from.yRot);
		to.setRotationZ(from.zRot);
	}
}
