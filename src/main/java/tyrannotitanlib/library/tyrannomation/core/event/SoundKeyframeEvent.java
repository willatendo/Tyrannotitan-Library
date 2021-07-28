/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package tyrannotitanlib.library.tyrannomation.core.event;

import tyrannotitanlib.library.tyrannomation.core.controller.AnimationController;

public class SoundKeyframeEvent<T> extends KeyframeEvent<T>
{
	public final String sound;

	public SoundKeyframeEvent(T entity, double animationTick, String sound, AnimationController controller)
	{
		super(entity, animationTick, controller);
		this.sound = sound;
	}
}
