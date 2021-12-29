package tyrannotitanlib.library.tyrannobook.client.data;

import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.library.tyrannobook.client.BookTextures;

@OnlyIn(Dist.CLIENT)
public class AppearanceData implements IDataItem {
	private ResourceLocation coverTexture;
	public String title = "";
	public String subtitle = "";
	public int coverColor = 0x8B4631;
	public int coverTextColor = 0xae8000;

	private ResourceLocation bookTexture;
	public int arrowColor = 0xffffd3;
	public int arrowColorHover = 0xff541c;
	public int hoverColor = 0x77ee541c;
	public int pageTint = 0xffffff;
	public boolean drawPageNumbers = true;
	public boolean drawSectionListText = false;
	public boolean drawFourColumnIndex = false;

	public boolean centerPageTitles = false;
	public boolean largePageTitles = false;

	public int slotColor = 0xff844c;
	public int lockedSectionColor = 0x000000;
	public int structureButtonColor = 0xe3e3bc;
	public int structureButtonColorHovered = 0x76d1e8;
	public int structureButtonColorToggled = 0x67c768;

	public float scale = 0.5F;

	public ResourceLocation getCoverTexture() {
		return Objects.requireNonNullElse(coverTexture, BookTextures.TEX_BOOKFRONT);
	}

	public ResourceLocation getBookTexture() {
		return Objects.requireNonNullElse(bookTexture, BookTextures.TEX_BOOK);
	}

	@Override
	public void load() {
	}
}
