package tyrannotitanlib.library.base.itemgroup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabBuilder extends CreativeModeTab {
	public ItemStack itemIcon;

	public TabBuilder(String modId, String tabId) {
		super(modId + "." + tabId);
	}

	@Override
	public ItemStack makeIcon() {
		return itemIcon;
	}

	// Set this in a Common event
	public void setIcon(ItemStack icon) {
		this.itemIcon = icon;
	}
}
