package tyrannotitanlib.tyrannimation.file;

import static tyrannotitanlib.core.content.Util.LOG;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.eliotlash.molang.MolangParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import tyrannotitanlib.tyrannimation.core.builder.Animation;
import tyrannotitanlib.tyrannimation.util.json.JsonAnimationUtils;

public class AnimatedFileLoader {
	public AnimatedFile loadAllAnimations(MolangParser parser, ResourceLocation location, ResourceManager manager) {
		AnimatedFile animationFile = new AnimatedFile();
		JsonObject jsonRepresentation = loadFile(location, manager);
		Set<Map.Entry<String, JsonElement>> entrySet = JsonAnimationUtils.getAnimations(jsonRepresentation);
		for (Map.Entry<String, JsonElement> entry : entrySet) {
			String animationName = entry.getKey();
			Animation animation;
			try {
				animation = JsonAnimationUtils.deserializeJsonToAnimation(JsonAnimationUtils.getAnimation(jsonRepresentation, animationName), parser);
				animationFile.putAnimation(animationName, animation);
			} catch (ChainedJsonException e) {
				LOG.error("Could not load animation: {}", animationName, e);
				throw new RuntimeException(e);
			}
		}
		return animationFile;
	}

	private JsonObject loadFile(ResourceLocation location, ResourceManager manager) {
		String content = getResourceAsString(location, manager);
		Gson GSON = new Gson();
		return GsonHelper.fromJson(GSON, content, JsonObject.class);
	}

	public static String getResourceAsString(ResourceLocation location, ResourceManager manager) {
		try (InputStream inputStream = manager.getResource(location).getInputStream()) {
			return IOUtils.toString(inputStream);
		} catch (Exception e) {
			String message = "Couldn't load " + location;
			LOG.error(message, e);
			throw new RuntimeException(new FileNotFoundException(location.toString()));
		}
	}
}
