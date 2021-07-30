package tyrannotitanlib.library.base.item;

import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import tyrannotitanlib.library.TyrannotitanMod;

public class TyrannoInformationItem extends Item
{
	private TextFormatting formating;
	private String translationtext;

	public TyrannoInformationItem(Properties properties, String translationText, TextFormatting formating) 
	{
		super(properties);
		this.translationtext = translationText;
		this.formating = formating;
	}

	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) 
	{
		if(TyrannotitanMod.id != null)
		{
			if(InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT))
			{
				TranslationTextComponent text = new TranslationTextComponent("toolTip." + TyrannotitanMod.id + "." + translationtext + ".holdingKey");
				text.withStyle(formating);
				tooltip.add(text);
			}
			else
			{
				TranslationTextComponent text = new TranslationTextComponent("toolTip." + TyrannotitanMod.id + "." + translationtext + ".notHoldingKey");
				text.withStyle(formating);
				tooltip.add(text);
			}
		}
	}
}
