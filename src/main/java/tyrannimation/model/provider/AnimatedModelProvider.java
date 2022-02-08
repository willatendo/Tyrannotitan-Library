package tyrannimation.model.provider;

import net.minecraft.resources.ResourceLocation;
import tyrannimation.animation.render.built.TyrannomationModel;
import tyrannimation.resource.TyrannomationCache;

public abstract class AnimatedModelProvider<T> {
	public double seekTime;
	public double lastGameTickTime;
	public boolean shouldCrashOnMissing = false;

	public TyrannomationModel getModel(ResourceLocation location) {
		return TyrannomationCache.getInstance().getTyrannoModels().get(location);
	}

	public abstract ResourceLocation getModelLocation(T object);

	public abstract ResourceLocation getTextureLocation(T object);
}
