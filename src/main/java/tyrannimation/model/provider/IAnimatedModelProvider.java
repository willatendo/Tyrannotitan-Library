package tyrannimation.model.provider;

import net.minecraft.resources.ResourceLocation;

public interface IAnimatedModelProvider<E> {
	ResourceLocation getAnimationFileLocation(E animatable);
}
