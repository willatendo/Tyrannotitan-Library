package tyrannotitanlib.tyrannimation.animation.raw.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MinecraftGeometry {
	private Bone[] bones;
	private String cape;
	private ModelProperties modelProperties;

	@JsonProperty("bones")
	public Bone[] getBones() {
		return this.bones;
	}

	@JsonProperty("bones")
	public void setBones(Bone[] value) {
		this.bones = value;
	}

	@JsonProperty("cape")
	public String getCape() {
		return this.cape;
	}

	@JsonProperty("cape")
	public void setCape(String value) {
		this.cape = value;
	}

	@JsonProperty("description")
	public ModelProperties getProperties() {
		return this.modelProperties;
	}

	@JsonProperty("description")
	public void setProperties(ModelProperties value) {
		this.modelProperties = value;
	}
}
