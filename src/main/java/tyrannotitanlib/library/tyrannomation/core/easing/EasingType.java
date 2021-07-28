package tyrannotitanlib.library.tyrannomation.core.easing;

public enum EasingType
{
	NONE, 
	CUSTOM, 
	LINEAR, 
	STEP, 
	EASEINSINE, 
	EASEOUTSINE, 
	EASEINOUTSINE, 
	EASEINQUAD, 
	EASEOUTQUAD, 
	EASEINOUTQUAD, 
	EASEINCUBIC, 
	EASEOUTCUBIC, 
	EASEINOUTCUBIC, 
	EASEINQUART, 
	EASEOUTQUART, 
	EASEINOUTQUART, 
	EASEINQUINT, 
	EASEOUTQUINT, 
	EASEINOUTQUINT, 
	EASEINEXPO, 
	EASEOUTEXPO, 
	EASEINOUTEXPO, 
	EASEINCIRC, 
	EASEOUTCIRC, 
	EASEINOUTCIRC, 
	EASEINBACK, 
	EASEOUTBACK, 
	EASEINOUTBACK, 
	EASEINELASTIC, 
	EASEOUTELASTIC, 
	EASEINOUTELASTIC, 
	EASEINBOUNCE, 
	EASEOUTBOUNCE, 
	EASEINOUTBOUNCE;

	public static EasingType getEasingTypeFromString(String search) 
	{
		for(EasingType each : EasingType.values()) 
		{
			if(each.name().compareToIgnoreCase(search) == 0) 
			{
				return each;
			}
		}
		return null;
	}
}
