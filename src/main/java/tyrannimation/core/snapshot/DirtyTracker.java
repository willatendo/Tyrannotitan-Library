package tyrannimation.core.snapshot;

import tyrannimation.core.processor.IAnimatedBone;

public class DirtyTracker {
	public IAnimatedBone model;
	public boolean hasScaleChanged;
	public boolean hasPositionChanged;
	public boolean hasRotationChanged;

	public DirtyTracker(boolean hasScaleChanged, boolean hasPositionChanged, boolean hasRotationChanged, IAnimatedBone model) {
		this.hasScaleChanged = hasScaleChanged;
		this.hasPositionChanged = hasPositionChanged;
		this.hasRotationChanged = hasRotationChanged;
		this.model = model;
	}
}
