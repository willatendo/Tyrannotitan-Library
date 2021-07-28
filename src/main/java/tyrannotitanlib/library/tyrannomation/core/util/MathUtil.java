package tyrannotitanlib.library.tyrannomation.core.util;

import java.util.function.Function;

import tyrannotitanlib.library.tyrannomation.core.easing.EasingManager;
import tyrannotitanlib.library.tyrannomation.core.easing.EasingType;
import tyrannotitanlib.library.tyrannomation.core.keyframe.AnimationPoint;

public class MathUtil
{
	public static float lerpValues(AnimationPoint animationPoint, EasingType easingType, Function<Double, Double> customEasingMethod)
	{
		if(animationPoint.currentTick >= animationPoint.animationEndTick)
		{
			return animationPoint.animationEndValue.floatValue();
		}
		
		if(animationPoint.currentTick == 0 && animationPoint.animationEndTick == 0)
		{
			return animationPoint.animationEndValue.floatValue();
		}

		if(easingType == EasingType.CUSTOM && customEasingMethod != null)
		{
			return lerpValues(customEasingMethod.apply(animationPoint.currentTick / animationPoint.animationEndTick),
					animationPoint.animationStartValue, animationPoint.animationEndValue);
		}
		else if(easingType == EasingType.NONE && animationPoint.keyframe != null)
		{
			easingType = animationPoint.keyframe.easingType;
		}
		double ease = EasingManager.ease(animationPoint.currentTick / animationPoint.animationEndTick, easingType, animationPoint.keyframe == null ? null : animationPoint.keyframe.easingArgs);
		return lerpValues(ease, animationPoint.animationStartValue, animationPoint.animationEndValue);
	}

	public static float lerpValues(double percentCompleted, double startValue, double endValue)
	{
		return (float) lerp(percentCompleted, startValue, endValue);
	}

	public static double lerp(double pct, double start, double end) 
	{
		return start + pct * (end - start);
	}
}
