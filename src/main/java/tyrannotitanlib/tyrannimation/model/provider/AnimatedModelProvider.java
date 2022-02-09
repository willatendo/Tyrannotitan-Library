package tyrannotitanlib.tyrannimation.model.provider;

import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.tyrannimation.animation.render.built.AnimationModel;
import tyrannotitanlib.tyrannimation.resource.AnimatedCashe;

public abstract class AnimatedModelProvider<T> {
	public double seekTime;
	public double lastGameTickTime;
	public boolean shouldCrashOnMissing = false;

	public AnimationModel getModel(ResourceLocation location) {
		return AnimatedCashe.getInstance().getTyrannoModels().get(location);
	}

	public abstract ResourceLocation getModelLocation(T object);

	public abstract ResourceLocation getTextureLocation(T object);
}
