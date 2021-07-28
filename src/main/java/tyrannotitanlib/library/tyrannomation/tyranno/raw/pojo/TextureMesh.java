package tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextureMesh 
{
	private double[] localPivot;
	private double[] position;
	private double[] rotation;
	private double[] scale;
	private String texture;

	@JsonProperty("local_pivot")
	public double[] getLocalPivot() 
	{
		return localPivot;
	}

	@JsonProperty("local_pivot")
	public void setLocalPivot(double[] value) 
	{
		this.localPivot = value;
	}

	@JsonProperty("position")
	public double[] getPosition() 
	{
		return position;
	}

	@JsonProperty("position")
	public void setPosition(double[] value) 
	{
		this.position = value;
	}

	@JsonProperty("rotation")
	public double[] getRotation() 
	{
		return rotation;
	}

	@JsonProperty("rotation")
	public void setRotation(double[] value) 
	{
		this.rotation = value;
	}

	@JsonProperty("scale")
	public double[] getScale() 
	{
		return scale;
	}

	@JsonProperty("scale")
	public void setScale(double[] value) 
	{
		this.scale = value;
	}
	
	@JsonProperty("texture")
	public String getTexture() 
	{
		return texture;
	}

	@JsonProperty("texture")
	public void setTexture(String value) 
	{
		this.texture = value;
	}
}
