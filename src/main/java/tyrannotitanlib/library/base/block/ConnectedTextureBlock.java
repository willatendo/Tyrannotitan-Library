package tyrannotitanlib.library.base.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class ConnectedTextureBlock extends Block 
{
	public final ResourceLocation texture;
	public final boolean connected;

	public ConnectedTextureBlock(Properties properties, String id, String texture, boolean connected) 
	{
		super(properties);
		this.texture = new ResourceLocation(id, texture);
		this.connected = connected;
	}
}
