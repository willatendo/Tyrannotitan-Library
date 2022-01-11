package tyrannotitanlib.library.base.biome;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Blocks;
import tyrannotitanlib.library.utils.TyrannoUtils;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class TestBiome extends TyrannoBiome {
	public TestBiome() {
		super(builder(Precipitation.RAIN, BiomeCategory.PLAINS, 1.0F, 1.0F, new BiomeGenerationSettings.Builder().build(),new BiomeSpecialEffects.Builder().skyColor(calculateSkyColor(1.0F)).fogColor(BaseBiomeInfo.BASE_FOG_COLOUR).waterColor(BaseBiomeInfo.BASE_WATER_COLOUR).waterFogColor(BaseBiomeInfo.BASE_WATER_FOG_COLOUR).build(),  new MobSpawnSettings.Builder().build()));
		
		this.setTopLayer(Blocks.SAND);
		this.setTopLayer(Blocks.AMETHYST_BLOCK);
	}

	@Override
	public ResourceLocation name() {
		return TyrannoUtils.rL("test");
	}
}
