package tyrannotitanlib.library.tyrannomation.model.provider;

import net.minecraft.util.ResourceLocation;

public interface ITyrannomatableModelProvider<E> 
{
	ResourceLocation getAnimationFileLocation(E animatable);
}
