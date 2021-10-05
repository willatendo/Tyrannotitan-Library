package tyrannotitanlib.library;

public enum OfficialMods 
{
	TL("tyrannotitanlib", "1.2.1"),
	LW("lostworlds", ".11"),
	H("hylanda", ".3"),
	ACM("achristmasmod", ".0"),
	CBM("creativebuildingmod", ".0"),
	CP("copperplus", ".0"),
	MR("minecraftrebaked", ".0"),
	EOM("endofminecraft", ".0");
	
	private final String id;
	private final String version;
	
	private OfficialMods(String id, String version) 
	{
		this.id = id;
		this.version = version;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public String getVersion()
	{
		return this.version;
	}
}
