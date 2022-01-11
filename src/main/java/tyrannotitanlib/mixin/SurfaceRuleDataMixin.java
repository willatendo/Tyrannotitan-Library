package tyrannotitanlib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList.Builder;

import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;
import tyrannotitanlib.library.base.biome.BiomeGeneration;
import tyrannotitanlib.library.base.biome.TyrannoBiome;

@Mixin(SurfaceRuleData.class)
public class SurfaceRuleDataMixin {
	@Inject(at = @At("RETURN"), method = "overworldLike", cancellable = true)
	private static void overworldLike(boolean b1, boolean b2, boolean b3, CallbackInfoReturnable<SurfaceRules.RuleSource> cir) {
		SurfaceRules.ConditionSource isNoWater = SurfaceRules.waterBlockCheck(-1, 0);
		Builder<SurfaceRules.RuleSource> builder = BiomeGeneration.overworldLike(b1, b2, b3);
		for (TyrannoBiome biomes : BiomeGeneration.BIOMES) {
			SurfaceRules.RuleSource modBiomeRules;
			modBiomeRules = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(biomes.getKey()), SurfaceRules.sequence(SurfaceRules.ifTrue(isNoWater, biomes.getTopBlock()), biomes.getMidBlock())));
			builder.add(modBiomeRules);
		}

		cir.setReturnValue(SurfaceRules.sequence(builder.build().toArray((array) -> {
			return new SurfaceRules.RuleSource[array];
		})));
	}
}
