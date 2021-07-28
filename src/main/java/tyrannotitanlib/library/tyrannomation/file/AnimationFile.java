package tyrannotitanlib.library.tyrannomation.file;

import java.util.Collection;
import java.util.HashMap;

import tyrannotitanlib.library.tyrannomation.core.builder.Animation;

public class AnimationFile 
{
	private HashMap<String, Animation> animations = new HashMap<>();

	public Animation getAnimation(String name) 
	{
		return animations.get(name);
	}

	public void putAnimation(String name, Animation animation) 
	{
		this.animations.put(name, animation);
	}

	public Collection<Animation> getAllAnimations() 
	{
		return this.animations.values();
	}
}
