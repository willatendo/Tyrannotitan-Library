/*
 * Copyright (c) 2020.
 * Author: Bernie G. (Gecko)
 */

package tyrannotitanlib.library.tyrannomation.core.keyframe;

import java.util.ArrayList;
import java.util.List;

public class VectorKeyFrameList<T extends KeyFrame>
{
	public List<T> xKeyFrames;
	public List<T> yKeyFrames;
	public List<T> zKeyFrames;

	public VectorKeyFrameList(List<T> XKeyFrames, List<T> YKeyFrames, List<T> ZKeyFrames)
	{
		xKeyFrames = XKeyFrames;
		yKeyFrames = YKeyFrames;
		zKeyFrames = ZKeyFrames;
	}

	public VectorKeyFrameList()
	{
		xKeyFrames = new ArrayList<>();
		yKeyFrames = new ArrayList<>();
		zKeyFrames = new ArrayList<>();
	}

	public double getLastKeyframeTime()
	{
		double xTime = 0;
		for(T frame : xKeyFrames)
		{
			xTime += frame.getLength();
		}

		double yTime = 0;
		for(T frame : yKeyFrames)
		{
			yTime += frame.getLength();
		}

		double zTime = 0;
		for(T frame : zKeyFrames)
		{
			zTime += frame.getLength();
		}

		return Math.max(xTime, Math.max(yTime, zTime));
	}
}
