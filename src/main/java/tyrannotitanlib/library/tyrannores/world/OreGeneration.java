package tyrannotitanlib.library.tyrannores.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import tyrannotitanlib.content.config.TyrannotitanConfig;
import tyrannotitanlib.library.base.biome.generation.TyrannoWorld;

public class OreGeneration 
{
	public static void addOresToOverworld(BiomeLoadingEvent event) 
	{
		if(TyrannotitanConfig.SERVER_CONFIG.copperOreGeneration.get()) 
		{
			TyrannoWorld.addOre(event, TyrannoreConfiguredFeatures.COPPER_ORE);
		}
	}
}
