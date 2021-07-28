package tyrannotitanlib.library.tyrannomation.core.builder;

import java.util.ArrayList;
import java.util.List;

public class AnimationBuilder
{
	private List<RawAnimation> animationList = new ArrayList<>();

	public AnimationBuilder addAnimation(String animationName, Boolean shouldLoop)
	{
		animationList.add(new RawAnimation(animationName, shouldLoop));
		return this;
	}
	
	public AnimationBuilder addAnimation(String animationName)
	{
		animationList.add(new RawAnimation(animationName, null));
		return this;
	}

	public AnimationBuilder addRepeatingAnimation(String animationName, int timesToRepeat)
	{
		assert timesToRepeat > 0;
		for(int i = 0; i < timesToRepeat; i++)
		{
			addAnimation(animationName, false);
		}
		return this;
	}
	
	public AnimationBuilder clearAnimations()
	{
		animationList.clear();
		return this;
	}

	public List<RawAnimation> getRawAnimationList()
	{
		return animationList;
	}

}
