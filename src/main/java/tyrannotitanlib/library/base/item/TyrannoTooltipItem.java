package tyrannotitanlib.library.base.item;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import tyrannotitanlib.library.TyrannotitanMod;

public class TyrannoTooltipItem extends Item
{
	private TextFormatting formating;
	private String translationtext;

	public TyrannoTooltipItem(Properties properties, String translationText, TextFormatting formating) 
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
			TranslationTextComponent text = new TranslationTextComponent("toolTip." + TyrannotitanMod.id + "." + translationtext);
			text.withStyle(formating);
			tooltip.add(text);
		}
	}
}
