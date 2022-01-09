package tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cube {
	private Double inflate;
	private Boolean mirror;
	private double[] origin = new double[] { 0, 0, 0 };
	private double[] pivot = new double[] { 0, 0, 0 };
	private double[] rotation = new double[] { 0, 0, 0 };
	private double[] size = new double[] { 1, 1, 1 };
	private UvUnion uv;

	@JsonProperty("inflate")
	public Double getInflate() {
		return inflate;
	}

	@JsonProperty("inflate")
	public void setInflate(Double value) {
		this.inflate = value;
	}

	@JsonProperty("mirror")
	public Boolean getMirror() {
		return mirror;
	}

	@JsonProperty("mirror")
	public void setMirror(Boolean value) {
		this.mirror = value;
	}

	@JsonProperty("origin")
	public double[] getOrigin() {
		return origin;
	}

	@JsonProperty("origin")
	public void setOrigin(double[] value) {
		this.origin = value;
	}

	@JsonProperty("pivot")
	public double[] getPivot() {
		return pivot;
	}

	@JsonProperty("pivot")
	public void setPivot(double[] value) {
		this.pivot = value;
	}

	@JsonProperty("rotation")
	public double[] getRotation() {
		return rotation;
	}

	@JsonProperty("rotation")
	public void setRotation(double[] value) {
		this.rotation = value;
	}

	@JsonProperty("size")
	public double[] getSize() {
		return size;
	}

	@JsonProperty("size")
	public void setSize(double[] value) {
		this.size = value;
	}

	@JsonProperty("uv")
	public UvUnion getUv() {
		return uv;
	}

	@JsonProperty("uv")
	public void setUv(UvUnion value) {
		this.uv = value;
	}
}
