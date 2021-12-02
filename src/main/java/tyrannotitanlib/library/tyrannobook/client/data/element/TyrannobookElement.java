package tyrannotitanlib.library.tyrannobook.client.data.element;

import java.util.List;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmlclient.gui.GuiUtils;
import tyrannotitanlib.library.tyrannobook.client.screen.TyrannobookScreen;

@OnlyIn(Dist.CLIENT)
public abstract class TyrannobookElement extends GuiComponent 
{
	public TyrannobookScreen parent;

	protected Minecraft mc = Minecraft.getInstance();
	protected TextureManager renderEngine = this.mc.textureManager;

	public int x, y;

	public TyrannobookElement(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

	public abstract void draw(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, Font fontRenderer);

	public void drawOverlay(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, Font fontRenderer) { }

	public void mouseClicked(double mouseX, double mouseY, int mouseButton) { }

	public void mouseReleased(double mouseX, double mouseY, int clickedMouseButton) { }

	public void mouseDragged(double clickX, double clickY, double mx, double my, double lastX, double lastY, int button) { }

	public void renderToolTip(PoseStack matrixStack, Font fontRenderer, ItemStack stack, int x, int y) 
	{
		List<Component> list = stack.getTooltipLines(this.mc.player, this.mc.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);

		Font font = mc.font;
		if(font == null) 
		{
			font = fontRenderer;
		}

		this.drawHoveringText(matrixStack, list, x, y, font);
		Lighting.setupForFlatItems();
	}

	public void drawHoveringText(PoseStack matrixStack, List<Component> textLines, int x, int y, Font font) 
	{
		GuiUtils.drawHoveringText(matrixStack, textLines, x, y, this.parent.width, this.parent.height, -1, font);
		Lighting.setupForFlatItems();
	}
}
