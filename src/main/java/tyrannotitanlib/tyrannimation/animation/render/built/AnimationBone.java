package tyrannotitanlib.tyrannimation.animation.render.built;

import java.util.ArrayList;
import java.util.List;

import tyrannotitanlib.tyrannimation.core.processor.IAnimatedBone;
import tyrannotitanlib.tyrannimation.core.snapshot.BoneSnapshot;

public class AnimationBone implements IAnimatedBone {
	public AnimationBone parent;

	public List<AnimationBone> childBones = new ArrayList<>();
	public List<AnimationCube> childCubes = new ArrayList<>();

	public String name;
	private BoneSnapshot initialSnapshot;

	public Boolean mirror;
	public Double inflate;
	public Boolean dontRender;
	public boolean isHidden;

	public Boolean reset;

	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;

	private float positionX;
	private float positionY;
	private float positionZ;

	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;

	private float rotateX;
	private float rotateY;
	private float rotateZ;

	public Object extraData;

	@Override
	public void setModelRendererName(String modelRendererName) {
		this.name = modelRendererName;
	}

	@Override
	public void saveInitialSnapshot() {
		if (this.initialSnapshot == null) {
			this.initialSnapshot = new BoneSnapshot(this, true);
		}
	}

	@Override
	public BoneSnapshot getInitialSnapshot() {
		return this.initialSnapshot;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getRotationX() {
		return this.rotateX;
	}

	@Override
	public float getRotationY() {
		return this.rotateY;
	}

	@Override
	public float getRotationZ() {
		return this.rotateZ;
	}

	@Override
	public float getPositionX() {
		return this.positionX;
	}

	@Override
	public float getPositionY() {
		return this.positionY;
	}

	@Override
	public float getPositionZ() {
		return this.positionZ;
	}

	@Override
	public float getScaleX() {
		return this.scaleX;
	}

	@Override
	public float getScaleY() {
		return this.scaleY;
	}

	@Override
	public float getScaleZ() {
		return this.scaleZ;
	}

	@Override
	public void setRotationX(float value) {
		this.rotateX = value;
	}

	@Override
	public void setRotationY(float value) {
		this.rotateY = value;
	}

	@Override
	public void setRotationZ(float value) {
		this.rotateZ = value;
	}

	@Override
	public void setPositionX(float value) {
		this.positionX = value;
	}

	@Override
	public void setPositionY(float value) {
		this.positionY = value;
	}

	@Override
	public void setPositionZ(float value) {
		this.positionZ = value;
	}

	@Override
	public void setScaleX(float value) {
		this.scaleX = value;
	}

	@Override
	public void setScaleY(float value) {
		this.scaleY = value;
	}

	@Override
	public void setScaleZ(float value) {
		this.scaleZ = value;
	}

	@Override
	public boolean isHidden() {
		return this.isHidden;
	}

	@Override
	public void setHidden(boolean hidden) {
		this.isHidden = hidden;
	}

	@Override
	public void setPivotX(float value) {
		this.rotationPointX = value;
	}

	@Override
	public void setPivotY(float value) {
		this.rotationPointY = value;
	}

	@Override
	public void setPivotZ(float value) {
		this.rotationPointZ = value;
	}

	@Override
	public float getPivotX() {
		return this.rotationPointX;
	}

	@Override
	public float getPivotY() {
		return this.rotationPointY;
	}

	@Override
	public float getPivotZ() {
		return this.rotationPointZ;
	}
}
