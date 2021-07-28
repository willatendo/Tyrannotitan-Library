package tyrannotitanlib.library.tyrannomation.tyranno.raw.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UvFaces 
{
	private FaceUv down;
	private FaceUv east;
	private FaceUv north;
	private FaceUv south;
	private FaceUv up;
	private FaceUv west;

	@JsonProperty("down")
	public FaceUv getDown() 
	{
		return down;
	}

	@JsonProperty("down")
	public void setDown(FaceUv value) 
	{
		this.down = value;
	}

	@JsonProperty("east")
	public FaceUv getEast() 
	{
		return east;
	}

	@JsonProperty("east")
	public void setEast(FaceUv value) 
	{
		this.east = value;
	}
	
	@JsonProperty("north")
	public FaceUv getNorth() 
	{
		return north;
	}

	@JsonProperty("north")
	public void setNorth(FaceUv value) 
	{
		this.north = value;
	}
	
	@JsonProperty("south")
	public FaceUv getSouth() 
	{
		return south;
	}

	@JsonProperty("south")
	public void setSouth(FaceUv value) 
	{
		this.south = value;
	}

	@JsonProperty("up")
	public FaceUv getUp() 
	{
		return up;
	}

	@JsonProperty("up")
	public void setUp(FaceUv value) 
	{
		this.up = value;
	}

	@JsonProperty("west")
	public FaceUv getWest() 
	{
		return west;
	}

	@JsonProperty("west")
	public void setWest(FaceUv value) 
	{
		this.west = value;
	}
}
