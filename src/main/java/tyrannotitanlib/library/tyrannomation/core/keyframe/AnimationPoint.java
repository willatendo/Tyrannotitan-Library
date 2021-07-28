package tyrannotitanlib.library.tyrannomation.core.keyframe;

import com.eliotlash.mclib.math.IValue;


public class AnimationPoint
{
	public final Double currentTick;
	public final Double animationEndTick;
	public final Double animationStartValue;
	public final Double animationEndValue;

	public final KeyFrame<IValue> keyframe;

	public AnimationPoint( KeyFrame<IValue> keyframe, Double currentTick, Double animationEndTick, Double animationStartValue, Double animationEndValue)
	{
		this.keyframe = keyframe;
		this.currentTick = currentTick;
		this.animationEndTick = animationEndTick;
		this.animationStartValue = animationStartValue;
		this.animationEndValue = animationEndValue;
	}

	public AnimationPoint(KeyFrame<IValue> keyframe, double tick, double animationEndTick, float animationStartValue, double animationEndValue)
	{
		this.keyframe = keyframe;
		this.currentTick = tick;
		this.animationEndTick = animationEndTick;
		this.animationStartValue = Double.valueOf(animationStartValue);
		this.animationEndValue = animationEndValue;
	}

	@Override
	public String toString()
	{
		return "Tick: " + currentTick + " | End Tick: " + animationEndTick + " | Start Value: " + animationStartValue + " | End Value: " + animationEndValue;
	}
}
