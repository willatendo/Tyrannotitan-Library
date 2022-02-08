package tyrannibook.data;

import java.util.Objects;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;
import tyrannibook.screen.book.BookTextures;

public class AppearanceData implements IDataItem {
	@Nullable
	private ResourceLocation coverTexture;
	public String title = "";
	public String subtitle = "";
	public int coverColor = 0x8B4631;
	@Setter
	@Getter
	private int coverTextColor = 0xAE8000;

	@Nullable
	private ResourceLocation bookTexture;
	public int arrowColor = 0xFFFFD3;
	public int arrowColorHover = 0xFF541C;
	public int hoverColor = 0x77EE541C;
	@Setter
	@Getter
	private int pageTint = 0xFFFFFF;
	public boolean drawPageNumbers = true;
	public boolean drawSectionListText = false;
	public boolean drawFourColumnIndex = false;

	public boolean centerPageTitles = false;
	public boolean largePageTitles = false;

	public int slotColor = 0xFF844C;
	public int lockedSectionColor = 0x000000;
	public int structureButtonColor = 0xe3E3BC;
	public int structureButtonColorHovered = 0x76D1E8;
	public int structureButtonColorToggled = 0x67C768;

	public float scale = 0.5F;

	public ResourceLocation getCoverTexture() {
		return Objects.requireNonNullElse(coverTexture, BookTextures.BOOKFRONT_TEXTURE);
	}

	public ResourceLocation getBookTexture() {
		return Objects.requireNonNullElse(bookTexture, BookTextures.BOOK_TEXTURE);
	}

	@Override
	public void load() {
	}
}
