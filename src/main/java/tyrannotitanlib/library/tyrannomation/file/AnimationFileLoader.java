package tyrannotitanlib.library.tyrannomation.file;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.eliotlash.molang.MolangParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.util.JSONException;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import tyrannotitanlib.library.base.util.TyrannoUtils;
import tyrannotitanlib.library.tyrannomation.core.builder.Animation;
import tyrannotitanlib.library.tyrannomation.util.json.JsonAnimationUtils;

public class AnimationFileLoader 
{
	public AnimationFile loadAllAnimations(MolangParser parser, ResourceLocation location, IResourceManager manager) 
	{
		AnimationFile animationFile = new AnimationFile();
		JsonObject jsonRepresentation = loadFile(location, manager);
		Set<Map.Entry<String, JsonElement>> entrySet = JsonAnimationUtils.getAnimations(jsonRepresentation);
		for(Map.Entry<String, JsonElement> entry : entrySet) 
		{
			String animationName = entry.getKey();
			Animation animation;
			try
			{
				animation = JsonAnimationUtils.deserializeJsonToAnimation(JsonAnimationUtils.getAnimation(jsonRepresentation, animationName), parser);
				animationFile.putAnimation(animationName, animation);
			} 
			catch(JSONException e) 
			{
				TyrannoUtils.LOGGER.error("Could not load animation: {}", animationName, e);
				throw new RuntimeException(e);
			}
		}
		return animationFile;
	}
	
	private JsonObject loadFile(ResourceLocation location, IResourceManager manager) 
	{
		String content = getResourceAsString(location, manager);
		Gson GSON = new Gson();
		return JSONUtils.fromJson(GSON, content, JsonObject.class);
	}

	public static String getResourceAsString(ResourceLocation location, IResourceManager manager) 
	{
		try(InputStream inputStream = manager.getResource(location).getInputStream()) 
		{
			return IOUtils.toString(inputStream);
		} 
		catch(Exception e) 
		{
			String message = "Couldn't load " + location;
			TyrannoUtils.LOGGER.error(message, e);
			throw new RuntimeException(new FileNotFoundException(location.toString()));
		}
	}
}
