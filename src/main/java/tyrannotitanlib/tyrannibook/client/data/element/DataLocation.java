package tyrannotitanlib.tyrannibook.client.data.element;

import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import tyrannotitanlib.tyrannibook.client.repository.TyrannobookRepository;

public class DataLocation implements IDataElement
{
	public String file;
	public transient ResourceLocation location;

	@Override
	public void load(TyrannobookRepository source) 
	{
		this.location = "$BLOCK_ATALS".equals(this.file) ? PlayerContainer.BLOCK_ATLAS : source.getResourceLocation(this.file, true);
	}
}
