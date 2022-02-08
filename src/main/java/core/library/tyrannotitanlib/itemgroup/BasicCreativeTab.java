package core.library.tyrannotitanlib.itemgroup;

import lombok.Setter;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BasicCreativeTab extends CreativeModeTab {
	@Setter
	private ItemStack itemIcon;

	public BasicCreativeTab(String modId, String tabId) {
		super(modId + "." + tabId);
	}

	@Override
	public ItemStack makeIcon() {
		return itemIcon;
	}
}
