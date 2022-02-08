package core.content;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public record UtilitiesRegistry(String id) {
	public Logger getLogger() {
		return LogManager.getLogger(id);
	}

	public ResourceLocation resource(String location) {
		return new ResourceLocation(id, location);
	}

	public ResourceLocation minecraftResource(String location) {
		return new ResourceLocation("minecraft", location);
	}

	public TranslatableComponent translationText(String key) {
		return new TranslatableComponent(key);
	}

	public TranslatableComponent idBoundTranslationText(String type, String key) {
		return translationText(type + "." + id + "." + key);
	}

	public TranslatableComponent idBoundArgsTranslationText(String type, String key, String args) {
		return new TranslatableComponent(type + "." + id + "." + key, args);
	}

	public TranslatableComponent idBoundFormatedText(String type, String key, ChatFormatting... colour) {
		TranslatableComponent text = idBoundTranslationText(type, key);
		text.withStyle(colour);
		return text;
	}

	public TranslatableComponent idBoundArgsFormatedText(String type, String key, String args, ChatFormatting... colour) {
		TranslatableComponent text = idBoundArgsTranslationText(type, key, args);
		text.withStyle(colour);
		return text;
	}

	public TranslatableComponent idBoundGreyText(String type, String key) {
		return idBoundFormatedText(type, key, ChatFormatting.GRAY);
	}
}
