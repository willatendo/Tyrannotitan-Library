package tyrannotitanlib.core.content;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public record UtilitiesRegistry(String id) {
	public Logger getLogger() {
		return LogManager.getLogger(id);
	}

	public ResourceLocation mod(String location) {
		return new ResourceLocation(id, location);
	}

	public ResourceLocation mc(String location) {
		return new ResourceLocation("minecraft", location);
	}

	public TranslatableComponent trans(String key) {
		return new TranslatableComponent(key);
	}

	public TranslatableComponent boundTrans(String type, String key) {
		return trans(type + "." + id + "." + key);
	}

	public TranslatableComponent boundArgsTrans(String type, String key, String args) {
		return new TranslatableComponent(type + "." + id + "." + key, args);
	}

	public TranslatableComponent formatedBoundTrans(String type, String key, ChatFormatting... colour) {
		TranslatableComponent text = boundTrans(type, key);
		text.withStyle(colour);
		return text;
	}

	public TranslatableComponent formatedBoundArgsTrans(String type, String key, String args, ChatFormatting... colour) {
		TranslatableComponent text = boundArgsTrans(type, key, args);
		text.withStyle(colour);
		return text;
	}

	public TranslatableComponent greyBoundText(String type, String key) {
		return formatedBoundTrans(type, key, ChatFormatting.GRAY);
	}
}
