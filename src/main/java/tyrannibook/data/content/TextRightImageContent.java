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

public class TextRightImageContent extends PageContent {
	public static final ResourceLocation ID = TYRANNO_UTILS.resource("text_right_image");

	@Getter
	public String title;
	public TextData[] text1;
	public TextData[] text2;
	public ImageData image;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int y = getTitleHeight();

		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
		}

		if (this.text1 != null && this.text1.length > 0) {
			list.add(new TextElement(0, y, BookScreen.PAGE_WIDTH - 55, 50, this.text1));
		}

		if (this.image != null && this.image.location != null) {
			list.add(new ImageElement(BookScreen.PAGE_WIDTH - 50, y, 50, 50, this.image));
		} else {
			list.add(new ImageElement(BookScreen.PAGE_WIDTH - 50, y, 50, 50, ImageData.MISSING));
		}

		if (this.text2 != null && this.text2.length > 0) {
			list.add(new TextElement(0, y + 55, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - 55 - y, this.text2));
		}
	}
}
