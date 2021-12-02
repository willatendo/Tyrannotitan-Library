package tyrannotitanlib.library.tyrannobook.client.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmlclient.gui.GuiUtils;
import tyrannotitanlib.library.tyrannobook.client.data.element.TextData;
import tyrannotitanlib.library.utils.TyrannoUtils;

@OnlyIn(Dist.CLIENT)
public class TextDataRenderer 
{
	@Nullable
	public static String drawText(PoseStack pose, int x, int y, int boxWidth, int boxHeight, TextData[] data, int mouseX, int mouseY, Font fr) 
	{
		List<Component> tooltip = new ArrayList<Component>();
		String action = drawText(pose, x, y, boxWidth, boxHeight, data, mouseX, mouseY, fr, tooltip);

		if(tooltip.size() > 0) 
		{
			drawTooltip(pose, tooltip, mouseX, mouseY, fr);
		}

		return action;
	}

	public static String drawText(PoseStack pose, int x, int y, int boxWidth, int boxHeight, TextData[] data, int mouseX, int mouseY, Font fr, List<Component> tooltip) 
	{
		String action = "";

		int atX = x;
		int atY = y;

		float prevScale = 1.F;

		for(TextData item : data) 
		{
			int box1X, box1Y, box1W = 9999, box1H = y + fr.lineHeight;
			int box2X, box2Y = 9999, box2W, box2H;
			int box3X = 9999, box3Y = 9999, box3W, box3H;

			if(item == null || item.text == null || item.text.isEmpty()) 
			{
				continue;
			}
			if(item.text.equals("\n")) 
			{
				atX = x;
				atY += fr.lineHeight;
				continue;
			}

			if(item.paragraph) 
			{
				atX = x;
				atY += fr.lineHeight * 2 * prevScale;
			}

			prevScale = item.scale;

			String modifiers = "";

			if(item.useOldColor) 
			{
				modifiers += ChatFormatting.getByName(item.color);
			}

			if(item.bold) 
			{
				modifiers += ChatFormatting.BOLD;
			}
			if(item.italic) 
			{
				modifiers += ChatFormatting.ITALIC;
			}
			if(item.underlined) 
			{
				modifiers += ChatFormatting.UNDERLINE;
			}
			if(item.strikethrough) 
			{
				modifiers += ChatFormatting.STRIKETHROUGH;
			}
			if(item.obfuscated) 
			{
				modifiers += ChatFormatting.OBFUSCATED;
			}

			String text = translateString(item.text);

			String[] split = cropStringBySize(text, modifiers, boxWidth, boxHeight - (atY - y), boxWidth - (atX - x), fr, item.scale);

			box1X = atX;
			box1Y = atY;
			box2X = x;
			box2W = x + boxWidth;

			for(int i = 0; i < split.length; i++) 
			{
				if(i == split.length - 1) 
				{
					box3X = atX;
					box3Y = atY;
				}

				String s = split[i];
				drawScaledString(pose, fr, modifiers + s, atX, atY, item.rgbColor, item.dropshadow, item.scale);

				if(i < split.length - 1) 
				{
					atY += fr.lineHeight;
					atX = x;
				}

				if(i == 0) 
				{
					box2Y = atY;

					if(atX == x) 
					{
						box1W = x + boxWidth;
					} 
					else 
					{
						box1W = atX;
					}
				}
			}

			box2H = atY;

			atX += fr.width(split[split.length - 1]) * item.scale;
			if(atX - x >= boxWidth) 
			{
				atX = x;
				atY += fr.lineHeight * item.scale;
			}

			box3W = atX;
			box3H = (int) (atY + fr.lineHeight * item.scale);

			if(item.tooltip != null && item.tooltip.length > 0) 
			{
				if((mouseX >= box1X && mouseX <= box1W && mouseY >= box1Y && mouseY <= box1H && box1X != box1W && box1Y != box1H) || (mouseX >= box2X && mouseX <= box2W && mouseY >= box2Y && mouseY <= box2H && box2X != box2W && box2Y != box2H) || (mouseX >= box3X && mouseX <= box3W && mouseY >= box3Y && mouseY <= box3H && box3X != box3W && box1Y != box3H)) 
				{
					tooltip.addAll(Arrays.asList(item.tooltip));
				}
			}

			if(item.action != null && !item.action.isEmpty()) 
			{
				if((mouseX >= box1X && mouseX <= box1W && mouseY >= box1Y && mouseY <= box1H && box1X != box1W && box1Y != box1H) || (mouseX >= box2X && mouseX <= box2W && mouseY >= box2Y && mouseY <= box2H && box2X != box2W && box2Y != box2H) || (mouseX >= box3X && mouseX <= box3W && mouseY >= box3Y && mouseY <= box3H && box3X != box3W && box1Y != box3H)) 
				{
					action = item.action;
				}
			}

			if(atY >= y + boxHeight) 
			{
				if(item.dropshadow) 
				{
					fr.drawShadow(pose, "...", atX, atY, 0);
				} 
				else 
				{
					fr.draw(pose, "...", atX, atY, 0);
				}
				break;
			}
			y = atY;
		}

		if(TyrannobookScreen.debug && action != null && !action.isEmpty()) 
		{
			tooltip.add(TextComponent.EMPTY);
			tooltip.add(new TextComponent("Action: " + action).withStyle(ChatFormatting.GRAY));
		}

		return action;
	}

	public static String translateString(String s) 
	{
		s = s.replace("$$(", "$\0(").replace(")$$", ")\0$");

		while(s.contains("$(") && s.contains(")$") && s.indexOf("$(") < s.indexOf(")$")) 
		{
			String loc = s.substring(s.indexOf("$(") + 2, s.indexOf(")$"));
			s = s.replace("$(" + loc + ")$", I18n.get(loc));
		}

		if(s.indexOf("$(") > s.indexOf(")$") || s.contains(")$")) 
		{
			TyrannoUtils.LOGGER.error("[Books] [TextDataRenderer] Detected unbalanced localization symbols \"$(\" and \")$\" in string: \"" + s + "\".");
		}

		return s.replace("$\0(", "$(").replace(")\0$", ")$");
	}

	public static String[] cropStringBySize(String s, String modifiers, int width, int height, Font fr, float scale) 
	{
		return cropStringBySize(s, modifiers, width, height, width, fr, scale);
	}

	public static String[] cropStringBySize(String s, String modifiers, int width, int height, int firstWidth, Font fr, float scale) 
	{
		int curWidth = 0;
		int curHeight = (int) (fr.lineHeight * scale);

		for(int i = 0; i < s.length(); i++) 
		{
			curWidth += fr.width(modifiers + s.charAt(i)) * scale;

			if(s.charAt(i) == '\n' || (curHeight == (int) (fr.lineHeight * scale) && curWidth > firstWidth) || (curHeight != (int) (fr.lineHeight * scale) && curWidth > width)) 
			{
				int oldI = i;
				if(s.charAt(i) != '\n') 
				{
					while(i >= 0 && s.charAt(i) != ' ') 
					{
						i--;
					}
					if(i <= 0) 
					{
						i = oldI;
					}
				} 
				else 
				{
					oldI++;
				}

				s = s.substring(0, i) + "\r" + StringUtils.stripStart(s.substring(i + (i == oldI ? 0 : 1)), " ");

				i++;
				curWidth = 0;
				curHeight += fr.lineHeight * scale;

				if(curHeight >= height) 
				{
					return s.substring(0, i).split("\r");
				}
			}
		}

		return s.split("\r");
	}

	public static void drawTooltip(PoseStack pose, List<Component> textLines, int mouseX, int mouseY, Font font) 
	{
		GuiUtils.drawHoveringText(pose, textLines, mouseX, mouseY, TyrannobookScreen.PAGE_WIDTH, TyrannobookScreen.PAGE_HEIGHT, TyrannobookScreen.PAGE_WIDTH, font);
		//Lighting.setupForFlatItems();
	}

	public static void drawScaledString(PoseStack pose, Font font, String text, float x, float y, int color, boolean dropShadow, float scale) 
	{
		pose.pushPose();
		pose.translate(x, y, 0);
		pose.scale(scale, scale, 1.0F);
		//RenderSystem.pushMatrix();
		//RenderSystem.translatef(x, y, 0);
		//RenderSystem.scalef(scale, scale, 1F);
		if(dropShadow) 
		{
			font.drawShadow(pose, text, 0, 0, color);
		} 
		else 
		{
			font.draw(pose, text, 0, 0, color);
		}
		pose.popPose();
		//RenderSystem.popMatrix();
	}

	@SuppressWarnings("unused")
	private static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) 
	{
		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		RenderSystem.disableTexture();
		//RenderSystem.disableAlphaTest();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		//RenderSystem.shadeModel(7425);
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder vertexBuffer = tessellator.getBuilder();
		vertexBuffer.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
		vertexBuffer.vertex((double) right, (double) top, 0D).color(f1, f2, f3, f).endVertex();
		vertexBuffer.vertex((double) left, (double) top, 0D).color(f1, f2, f3, f).endVertex();
		vertexBuffer.vertex((double) left, (double) bottom, 0D).color(f5, f6, f7, f4).endVertex();
		vertexBuffer.vertex((double) right, (double) bottom, 0D).color(f5, f6, f7, f4).endVertex();
		tessellator.end();
		//RenderSystem.shadeModel(7424);
		//RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}
}
