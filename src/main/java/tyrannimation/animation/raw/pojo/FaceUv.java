package tyrannimation.animation.raw.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FaceUv {
	private String materialInstance;
	private double[] uv;
	private double[] uvSize;

	@JsonProperty("material_instance")
	public String getMaterialInstance() {
		return materialInstance;
	}

	@JsonProperty("material_instance")
	public void setMaterialInstance(String value) {
		this.materialInstance = value;
	}

	@JsonProperty("uv")
	public double[] getUv() {
		return uv;
	}

	@JsonProperty("uv")
	public void setUv(double[] value) {
		this.uv = value;
	}

	@JsonProperty("uv_size")
	public double[] getUvSize() {
		return uvSize;
	}

	@JsonProperty("uv_size")
	public void setUvSize(double[] value) {
		this.uvSize = value;
	}
}
