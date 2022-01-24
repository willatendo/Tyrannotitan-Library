package tyrannotitanlib.library.tyrannobook;

import java.io.IOException;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tyrannotitanlib.content.TyrannoUtils;

@Mod.EventBusSubscriber(modid = TyrannoUtils.TYRANNO_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TyrannoShaders {
	private static ShaderInstance blockFullBrightShader;

	@SubscribeEvent
	static void registerShaders(RegisterShadersEvent event) throws IOException {
		event.registerShader(new ShaderInstance(event.getResourceManager(), TyrannoUtils.rL("block_fullbright"), DefaultVertexFormat.BLOCK), shader -> blockFullBrightShader = shader);
	}

	public static ShaderInstance getBlockFullBrightShader() {
		return blockFullBrightShader;
	}
}
