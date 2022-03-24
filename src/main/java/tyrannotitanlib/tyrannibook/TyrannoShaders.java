package tyrannotitanlib.tyrannibook;

import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_ID;
import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

import java.io.IOException;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TYRANNO_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TyrannoShaders {
	private static ShaderInstance blockFullBrightShader;

	@SubscribeEvent
	static void registerShaders(RegisterShadersEvent event) throws IOException {
		event.registerShader(new ShaderInstance(event.getResourceManager(), TYRANNO_UTILS.mod("block_fullbright"), DefaultVertexFormat.BLOCK), shader -> blockFullBrightShader = shader);
	}

	public static ShaderInstance getBlockFullBrightShader() {
		return blockFullBrightShader;
	}
}
