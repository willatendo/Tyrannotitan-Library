package tyrannotitanlib.library.tyrannobook.screen.book;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;

public interface ILayerRenderFunction {
	void draw(BookElement element, PoseStack stack, int mouseX, int mouseY, float partialTicks, Font fontRenderer);
}
