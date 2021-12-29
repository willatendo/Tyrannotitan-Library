package tyrannotitanlib.library.tyrannobook.client.data.element;

import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.library.tyrannobook.client.repository.TyrannobookRepository;

public class DataLocation implements IDataElement {
	public String file;
	public transient ResourceLocation location;

	@Override
	public void load(TyrannobookRepository source) {
		this.location = "$BLOCK_ATALS".equals(this.file) ? InventoryMenu.BLOCK_ATLAS : source.getResourceLocation(this.file, true);
	}
}
