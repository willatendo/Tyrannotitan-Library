package tyrannotitanlib.library.tyrannomation.model.provider;

import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.library.tyrannomation.resource.TyrannomationCache;
import tyrannotitanlib.library.tyrannomation.tyranno.render.built.TyrannomationModel;

public abstract class TyrannomationModelProvider<T> {
	public double seekTime;
	public double lastGameTickTime;
	public boolean shouldCrashOnMissing = false;

	public TyrannomationModel getModel(ResourceLocation location) {
		return TyrannomationCache.getInstance().getTyrannoModels().get(location);
	}

	public abstract ResourceLocation getModelLocation(T object);

	public abstract ResourceLocation getTextureLocation(T object);
}
