package tyrannotitanlib.tyrannibook.data.content;

import static tyrannotitanlib.core.content.Util.TYRANNO_UTILS;

import java.util.ArrayList;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.tyrannibook.data.TyrannobookData;
import tyrannotitanlib.tyrannibook.data.element.ImageData;
import tyrannotitanlib.tyrannibook.data.element.TextData;
import tyrannotitanlib.tyrannibook.screen.book.BookScreen;
import tyrannotitanlib.tyrannibook.screen.book.element.BookElement;
import tyrannotitanlib.tyrannibook.screen.book.element.ImageElement;
import tyrannotitanlib.tyrannibook.screen.book.element.TextElement;

public class ImageTextContent extends PageContent {
	public static final ResourceLocation ID = TYRANNO_UTILS.resource("image_text");

	@Getter
	public String title = null;
	public ImageData image;
	public TextData[] text;
	public boolean centerImage = true;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int y = getTitleHeight();

		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
		}

		if (this.image != null && this.image.location != null) {
			int x = 0;
			int width = BookScreen.PAGE_WIDTH;
			if (centerImage && this.image.width != -1) {
				x = (BookScreen.PAGE_WIDTH - this.image.width) / 2;
				width = this.image.width;
			}
			ImageElement element = new ImageElement(x, y, width, 100, this.image);
			list.add(element);
			y += element.height + 5;
		} else {
			list.add(new ImageElement(0, y, 32, 32, ImageData.MISSING));
			y += 37;
		}

		if (this.text != null && this.text.length > 0) {
			list.add(new TextElement(0, y, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - y, this.text));
		}
	}
}
