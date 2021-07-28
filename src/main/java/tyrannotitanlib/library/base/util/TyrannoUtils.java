package tyrannotitanlib.library.base.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import tyrannotitanlib.library.TyrannotitanMod;

/*
 * Lots of random utilities.
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

public class TyrannoUtils 
{
	public static final Logger LOGGER = LogManager.getLogger(TyrannoUtils.ID);
	public static boolean DISABLE_IN_DEV = false;
		
	public static final String TYRANNO_ID = "tyrannotitanlib";
	public static final String ID = TyrannotitanMod.id;
	
	public static ResourceLocation rL(String location)
	{
		return new ResourceLocation(ID, location);
	}
	
	public static TranslationTextComponent sTC(String key)
	{
		return new TranslationTextComponent(key);
	}
	
	public static TranslationTextComponent tTC(String type, String key)
	{
		return new TranslationTextComponent(type + "." + ID + "." + key);
	}
	
	public static TranslationTextComponent cTC(String type, String key, TextFormatting colour)
	{
		TranslationTextComponent text = tTC(type, key);
		text.withStyle(colour);
		return text;
	}
	
	public static TranslationTextComponent gTC(String type, String key)
	{
		TranslationTextComponent text = tTC(type, key);
		text.withStyle(TextFormatting.GRAY);
		return text;
	}
}
