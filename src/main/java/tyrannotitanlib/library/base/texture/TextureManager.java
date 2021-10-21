package tyrannotitanlib.library.base.texture;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import tyrannotitanlib.library.base.block.ConnectedTextureBlock;

public class TextureManager 
{
	public static final Map<ConnectedTextureBlock, TextureAtlasSprite> TEXTURES = new HashMap<>();
	public static final Map<ConnectedTextureBlock, TextureAtlasSprite> PARTICLES = new HashMap<>();
}
