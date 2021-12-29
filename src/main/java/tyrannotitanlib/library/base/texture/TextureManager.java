package tyrannotitanlib.library.base.texture;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import tyrannotitanlib.library.base.block.TyrannoConnectedTextureBlock;

public class TextureManager {
	public static final Map<TyrannoConnectedTextureBlock, TextureAtlasSprite> TEXTURES = new HashMap<>();
	public static final Map<TyrannoConnectedTextureBlock, TextureAtlasSprite> PARTICLES = new HashMap<>();
}
