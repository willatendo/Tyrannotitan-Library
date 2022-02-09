package tyrannotitanlib.tyrannimation.animation.exception;

import net.minecraft.resources.ResourceLocation;

public class AnimationException extends RuntimeException {
	public AnimationException(ResourceLocation fileLocation, String message) {
		super(fileLocation + ": " + message);
	}

	public AnimationException(ResourceLocation fileLocation, String message, Throwable cause) {
		super(fileLocation + ": " + message, cause);
	}
}
