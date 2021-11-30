package tyrannotitanlib.library.tyrannomation.tyranno.exception;

import net.minecraft.resources.ResourceLocation;

public class TyrannotitanLibException extends RuntimeException 
{
	public TyrannotitanLibException(ResourceLocation fileLocation, String message) 
	{
		super(fileLocation + ": " + message);
	}

	public TyrannotitanLibException(ResourceLocation fileLocation, String message, Throwable cause) 
	{
		super(fileLocation + ": " + message, cause);
	}
}
