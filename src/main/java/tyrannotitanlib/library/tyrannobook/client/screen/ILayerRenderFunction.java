package tyrannotitanlib.library.tyrannobook.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import tyrannotitanlib.library.tyrannobook.client.data.element.TyrannobookElement;

public interface ILayerRenderFunction {
	void draw(TyrannobookElement element, PoseStack pose, int mouseX, int mouseY, float partialTicks, Font fontRenderer);
}