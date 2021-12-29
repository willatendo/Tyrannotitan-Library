package tyrannotitanlib.library.tyrannobook.client.data.element;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button.OnPress;
import tyrannotitanlib.library.tyrannobook.client.screen.ArrowButton;

public class ArrowElement extends ButtonElement {
	protected final ArrowButton button;

	public ArrowElement(int x, int y, ArrowButton.ArrowType arrowType, int arrowColor, int arrowColorHover, OnPress iPressable) {
		super(x, y, arrowType.w, arrowType.h);
		this.button = new ArrowButton(null, x, y, arrowType, arrowColor, arrowColorHover, iPressable);
	}

	@Override
	public void draw(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, Font fontRenderer) {
		this.button.renderButton(matrixStack, mouseX, mouseY, partialTicks, parent.book);
	}

	@Override
	public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
		if (this.button != null && this.isHovered(mouseX, mouseY)) {
			this.button.onPress();
		}
	}
}
