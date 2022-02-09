package tyrannotitanlib.tyrannimation.animation.render.built;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

import com.mojang.math.Vector3f;

public class AnimationVertex {
	public final Vector3f position;
	public float textureU;
	public float textureV;

	public AnimationVertex(float x, float y, float z) {
		this.position = new Vector3f(x, y, z);
	}

	public AnimationVertex(double x, double y, double z) {
		this.position = new Vector3f((float) x, (float) y, (float) z);
	}

	public AnimationVertex setTextureUV(float texU, float texV) {
		return new AnimationVertex(this.position, texU, texV);
	}

	public AnimationVertex setTextureUV(double[] array) {
		Validate.validIndex(ArrayUtils.toObject(array), 1);
		return new AnimationVertex(this.position, (float) array[0], (float) array[1]);
	}

	public AnimationVertex(Vector3f posIn, float texU, float texV) {
		this.position = posIn;
		this.textureU = texU;
		this.textureV = texV;
	}
}