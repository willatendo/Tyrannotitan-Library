package tyrannotitanlib.library.tyrannomation.model.provider;

import net.minecraft.resources.ResourceLocation;

public interface ITyrannomatableModelProvider<E> 
{
	ResourceLocation getAnimationFileLocation(E animatable);
}
