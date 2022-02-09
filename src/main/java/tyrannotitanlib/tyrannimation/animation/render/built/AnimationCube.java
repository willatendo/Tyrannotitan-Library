package tyrannotitanlib.tyrannimation.animation.render.built;

import com.mojang.math.Vector3f;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.Cube;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.FaceUv;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.ModelProperties;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.UvFaces;
import tyrannotitanlib.tyrannimation.animation.raw.pojo.UvUnion;
import tyrannotitanlib.tyrannimation.util.VectorUtils;

public class AnimationCube {
	public AnimationQuad[] quads = new AnimationQuad[6];
	public Vector3f pivot;
	public Vector3f rotation;
	public Vector3f size = new Vector3f();
	public double inflate;
	public Boolean mirror;

	private AnimationCube(double[] size) {
		if (size.length >= 3) {
			this.size.set((float) size[0], (float) size[1], (float) size[2]);
		}
	}

	public static AnimationCube createFromPojoCube(Cube cubeIn, ModelProperties properties, Double boneInflate, Boolean mirror) {
		AnimationCube cube = new AnimationCube(cubeIn.getSize());

		UvUnion uvUnion = cubeIn.getUv();
		UvFaces faces = uvUnion.faceUV;
		boolean isBoxUV = uvUnion.isBoxUV;
		cube.mirror = cubeIn.getMirror();
		cube.inflate = cubeIn.getInflate() == null ? (boneInflate == null ? 0 : boneInflate) : cubeIn.getInflate() / 16;

		float textureHeight = properties.getTextureHeight().floatValue();
		float textureWidth = properties.getTextureWidth().floatValue();

		Vec3 size = VectorUtils.fromArray(cubeIn.getSize());
		Vec3 origin = VectorUtils.fromArray(cubeIn.getOrigin());
		origin = new Vec3(-(origin.x + size.x) / 16, origin.y / 16, origin.z / 16);

		size = size.multiply(0.0625f, 0.0625, 0.0625f);

		Vector3f rotation = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(cubeIn.getRotation()));
		rotation.mul(-1, -1, 1);

		rotation.setX((float) Math.toRadians(rotation.x()));
		rotation.setY((float) Math.toRadians(rotation.y()));
		rotation.setZ((float) Math.toRadians(rotation.z()));

		Vector3f pivot = VectorUtils.convertDoubleToFloat(VectorUtils.fromArray(cubeIn.getPivot()));
		pivot.mul(-1, 1, 1);

		cube.pivot = pivot;
		cube.rotation = rotation;

		AnimationVertex P1 = new AnimationVertex(origin.x - cube.inflate, origin.y - cube.inflate, origin.z - cube.inflate);
		AnimationVertex P2 = new AnimationVertex(origin.x - cube.inflate, origin.y - cube.inflate, origin.z + size.z + cube.inflate);
		AnimationVertex P3 = new AnimationVertex(origin.x - cube.inflate, origin.y + size.y + cube.inflate, origin.z - cube.inflate);
		AnimationVertex P4 = new AnimationVertex(origin.x - cube.inflate, origin.y + size.y + cube.inflate, origin.z + size.z + cube.inflate);
		AnimationVertex P5 = new AnimationVertex(origin.x + size.x + cube.inflate, origin.y - cube.inflate, origin.z - cube.inflate);
		AnimationVertex P6 = new AnimationVertex(origin.x + size.x + cube.inflate, origin.y - cube.inflate, origin.z + size.z + cube.inflate);
		AnimationVertex P7 = new AnimationVertex(origin.x + size.x + cube.inflate, origin.y + size.y + cube.inflate, origin.z - cube.inflate);
		AnimationVertex P8 = new AnimationVertex(origin.x + size.x + cube.inflate, origin.y + size.y + cube.inflate, origin.z + size.z + cube.inflate);

		AnimationQuad quadWest;
		AnimationQuad quadEast;
		AnimationQuad quadNorth;
		AnimationQuad quadSouth;
		AnimationQuad quadUp;
		AnimationQuad quadDown;

		if (!isBoxUV) {
			FaceUv west = faces.getWest();
			FaceUv east = faces.getEast();
			FaceUv north = faces.getNorth();
			FaceUv south = faces.getSouth();
			FaceUv up = faces.getUp();
			FaceUv down = faces.getDown();

			quadWest = west == null ? null : new AnimationQuad(new AnimationVertex[] { P4, P3, P1, P2 }, west.getUv(), west.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.WEST);
			quadEast = east == null ? null : new AnimationQuad(new AnimationVertex[] { P7, P8, P6, P5 }, east.getUv(), east.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.EAST);
			quadNorth = north == null ? null : new AnimationQuad(new AnimationVertex[] { P3, P7, P5, P1 }, north.getUv(), north.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.NORTH);
			quadSouth = south == null ? null : new AnimationQuad(new AnimationVertex[] { P8, P4, P2, P6 }, south.getUv(), south.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.SOUTH);
			quadUp = up == null ? null : new AnimationQuad(new AnimationVertex[] { P4, P8, P7, P3 }, up.getUv(), up.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.UP);
			quadDown = down == null ? null : new AnimationQuad(new AnimationVertex[] { P1, P5, P6, P2 }, down.getUv(), down.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.DOWN);

			if (cubeIn.getMirror() == Boolean.TRUE || mirror == Boolean.TRUE) {
				quadWest = west == null ? null : new AnimationQuad(new AnimationVertex[] { P7, P8, P6, P5 }, west.getUv(), west.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.WEST);
				quadEast = east == null ? null : new AnimationQuad(new AnimationVertex[] { P4, P3, P1, P2 }, east.getUv(), east.getUvSize(), textureWidth, textureHeight, cubeIn.getMirror(), Direction.EAST);
			}
		} else {
			double[] UV = cubeIn.getUv().boxUVCoords;
			Vec3 UVSize = VectorUtils.fromArray(cubeIn.getSize());
			UVSize = new Vec3(Math.floor(UVSize.x), Math.floor(UVSize.y), Math.floor(UVSize.z));

			quadWest = new AnimationQuad(new AnimationVertex[] { P4, P3, P1, P2 }, new double[] { UV[0] + UVSize.z + UVSize.x, UV[1] + UVSize.z }, new double[] { UVSize.z, UVSize.y }, textureWidth, textureHeight, cubeIn.getMirror(), Direction.WEST);
			quadEast = new AnimationQuad(new AnimationVertex[] { P7, P8, P6, P5 }, new double[] { UV[0], UV[1] + UVSize.z }, new double[] { UVSize.z, UVSize.y }, textureWidth, textureHeight, cubeIn.getMirror(), Direction.EAST);
			quadNorth = new AnimationQuad(new AnimationVertex[] { P3, P7, P5, P1 }, new double[] { UV[0] + UVSize.z, UV[1] + UVSize.z }, new double[] { UVSize.x, UVSize.y }, textureWidth, textureHeight, cubeIn.getMirror(), Direction.NORTH);
			quadSouth = new AnimationQuad(new AnimationVertex[] { P8, P4, P2, P6 }, new double[] { UV[0] + UVSize.z + UVSize.x + UVSize.z, UV[1] + UVSize.z }, new double[] { UVSize.x, UVSize.y }, textureWidth, textureHeight, cubeIn.getMirror(), Direction.SOUTH);
			quadUp = new AnimationQuad(new AnimationVertex[] { P4, P8, P7, P3 }, new double[] { UV[0] + UVSize.z, UV[1] }, new double[] { UVSize.x, UVSize.z }, textureWidth, textureHeight, cubeIn.getMirror(), Direction.UP);
			quadDown = new AnimationQuad(new AnimationVertex[] { P1, P5, P6, P2 }, new double[] { UV[0] + UVSize.z + UVSize.x, UV[1] + UVSize.z }, new double[] { UVSize.x, -UVSize.z }, textureWidth, textureHeight, cubeIn.getMirror(), Direction.DOWN);

			if (cubeIn.getMirror() == Boolean.TRUE || mirror == Boolean.TRUE) {
				quadWest = new AnimationQuad(new AnimationVertex[] { P7, P8, P6, P5 }, new double[] { UV[0] + UVSize.z + UVSize.x, UV[1] + UVSize.z }, new double[] { UVSize.z, UVSize.y }, textureWidth, textureHeight, cubeIn.getMirror(), Direction.WEST);
				quadEast = new AnimationQuad(new AnimationVertex[] { P4, P3, P1, P2 }, new double[] { UV[0], UV[1] + UVSize.z }, new double[] { UVSize.z, UVSize.y }, textureWidth, textureHeight, cubeIn.getMirror(), Direction.EAST);
			}
		}

		cube.quads[0] = quadWest;
		cube.quads[1] = quadEast;
		cube.quads[2] = quadNorth;
		cube.quads[3] = quadSouth;
		cube.quads[4] = quadUp;
		cube.quads[5] = quadDown;
		return cube;
	}
}
