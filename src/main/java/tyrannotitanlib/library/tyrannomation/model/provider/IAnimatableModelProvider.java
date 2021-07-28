package tyrannotitanlib.library.tyrannomation.model.provider;

import net.minecraft.util.ResourceLocation;

public interface IAnimatableModelProvider<E> 
{
	ResourceLocation getAnimationFileLocation(E animatable);
}
