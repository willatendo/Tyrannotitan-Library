package tyrannotitanlib.content.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import tyrannotitanlib.content.server.ServerConfig;

public class TyrannotitanConfig 
{
	public static final ForgeConfigSpec serverConfig;
	public static final ServerConfig SERVER_CONFIG;
	
	static
	{
		final Pair<ServerConfig, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		serverConfig = serverSpecPair.getRight();
		SERVER_CONFIG = serverSpecPair.getLeft();
	}
}
