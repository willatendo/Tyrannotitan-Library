package tyrannotitanlib.tyrannibook.data.element;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import tyrannotitanlib.tyrannibook.repository.BookRepository;

public class DataLocation implements IDataElement {
	public String file;
	public transient ResourceLocation location;

	@Override
	public void load(BookRepository source) {
		this.location = "$BLOCK_ATLAS".equals(this.file) ? InventoryMenu.BLOCK_ATLAS : source.getResourceLocation(this.file, true);
	}
}
