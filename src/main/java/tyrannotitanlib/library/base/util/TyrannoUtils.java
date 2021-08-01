package tyrannotitanlib.library.base.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

/*
 * This class holds a whole bunch of constants and utilities such as the library Modid, Logger, Resource Locations, and Translation Text Components. (@Willatendo)
 */

public class TyrannoUtils 
{
	//Logger
	public static final Logger LOGGER = LogManager.getLogger(TyrannoUtils.TYRANNO_ID);
	
	//Library ModId
	public static final String TYRANNO_ID = "tyrannotitanlib";
	
	//ResourceLocation set up and with Library ModId
	public static ResourceLocation rL(String location)
	{
		return new ResourceLocation(TYRANNO_ID, location);
	}
	
	//Simple TranslationTextComponent
	public static TranslationTextComponent sTC(String key)
	{
		return new TranslationTextComponent(key);
	}
	
	//TranslationTextComponent set up and with Library ModId
	public static TranslationTextComponent tTC(String type, String key)
	{
		return new TranslationTextComponent(type + "." + TYRANNO_ID + "." + key);
	}
	
	//TranslationTextComponent set up, with Library ModId, and colour
	public static TranslationTextComponent cTC(String type, String key, TextFormatting colour)
	{
		TranslationTextComponent text = tTC(type, key);
		text.withStyle(colour);
		return text;
	}
	
	//TranslationTextComponent set up, with Library ModId, and sets the colour to grey
	public static TranslationTextComponent gTC(String type, String key)
	{
		TranslationTextComponent text = tTC(type, key);
		text.withStyle(TextFormatting.GRAY);
		return text;
	}
}
