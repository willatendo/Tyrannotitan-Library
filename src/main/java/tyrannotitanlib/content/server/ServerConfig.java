package tyrannotitanlib.content.server;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ServerConfig 
{
	protected static final String TRANSLATION_TEXT = "tyrannotitanlib.config.";

	public final BooleanValue copperOreGeneration;
	public final IntValue copperVeinSize;
	public final IntValue copperRange;
	public final IntValue copperCountPerChunk;

	public ServerConfig(ForgeConfigSpec.Builder builder) 
	{
		this.copperOreGeneration = builder.comment("Sets if copper ore should spawn.").translation(TRANSLATION_TEXT + "copperOreGeneration").define("copperOreGeneration", true);
		this.copperVeinSize = builder.comment("Sets the size a copper vein can spawn.").translation(TRANSLATION_TEXT + "copperVeinSize").defineInRange("copperVeinSize", 9, 1, 100);
		this.copperRange = builder.comment("Sets the range copper can spawn in.").translation(TRANSLATION_TEXT + "copperRange").defineInRange("copperRange", 64, 1, 256);
		this.copperCountPerChunk = builder.comment("Sets the amount of copper veins per chunk.").translation(TRANSLATION_TEXT + "copperCountPerChunk").defineInRange("copperCountPerChunk", 20, 1, 100);
	}
}
