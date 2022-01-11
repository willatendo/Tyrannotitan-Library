package tyrannotitanlib.library.tyrannomation.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import tyrannotitanlib.library.tyrannomation.model.provider.TyrannomationModelProvider;
import tyrannotitanlib.library.tyrannomation.renderers.ITyrannomationRenderer;
import tyrannotitanlib.library.tyrannomationcore.processor.IBone;

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

	public static <T extends Entity> TyrannomationModelProvider getTyrannoModelForEntity(T entity) {
		EntityRenderer<T> entityRenderer = getRenderer(entity);

		if (entityRenderer instanceof ITyrannomationRenderer) {
			return ((ITyrannomationRenderer<?>) entityRenderer).getTyrannoModelProvider();
		}
		return null;
	}

	public static void copyRotations(ModelPart from, IBone to) {
		to.setRotationX(-from.xRot);
		to.setRotationY(-from.yRot);
		to.setRotationZ(from.zRot);
	}
}
