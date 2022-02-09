package tyrannotitanlib.library.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class TyrannoConnectedTextureBlock extends Block {
	public final ResourceLocation texture;
	public final boolean connected;

	public TyrannoConnectedTextureBlock(Properties properties, String id, String texture, boolean connected) {
		super(properties);
		this.texture = new ResourceLocation(id, texture);
		this.connected = connected;
	}
}
