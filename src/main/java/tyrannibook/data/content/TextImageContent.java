package tyrannibook.data.content;

import static core.content.Util.TYRANNO_UTILS;

import java.util.ArrayList;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import tyrannibook.data.TyrannobookData;
import tyrannibook.data.element.ImageData;
import tyrannibook.data.element.TextData;
import tyrannibook.screen.book.BookScreen;
import tyrannibook.screen.book.element.BookElement;
import tyrannibook.screen.book.element.ImageElement;
import tyrannibook.screen.book.element.TextElement;

public class TextImageContent extends PageContent {
	public static final ResourceLocation ID = TYRANNO_UTILS.resource("text_image");

	@Getter
	public String title = null;
	public TextData[] text;
	public ImageData image;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int y = getTitleHeight();

		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
		}

		if (this.text != null && this.text.length > 0) {
			list.add(new TextElement(0, y, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - 105, this.text));
		}

		if (this.image != null && this.image.location != null) {
			list.add(new ImageElement(0, y + BookScreen.PAGE_HEIGHT - 100, BookScreen.PAGE_WIDTH, 100 - y, this.image));
		} else {
			list.add(new ImageElement(0, y + BookScreen.PAGE_HEIGHT - 100, BookScreen.PAGE_WIDTH, 100 - y, ImageData.MISSING));
		}
	}
}
