package tyrannotitanlib.library.tyrannomation.core.builder;

import java.util.Objects;

public class RawAnimation
{
	public String animationName;

	public Boolean loop;

	public RawAnimation(String animationName, Boolean loop)
	{
		this.animationName = animationName;
		this.loop = loop;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == this) 
			return true;
		
		if(!(obj instanceof RawAnimation))
		{
			return false;
		}
		
		RawAnimation animation = (RawAnimation) obj;
		
		if(animation.loop == this.loop && animation.animationName.equals(this.animationName))
		{
			return true;
		}
		
		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(animationName, loop);
	}
}
