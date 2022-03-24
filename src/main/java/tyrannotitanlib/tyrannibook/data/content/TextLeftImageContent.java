package tyrannotitanlib.tyrannibook.data.content;

import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

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

public class TextLeftImageContent extends PageContent {
	public static final ResourceLocation ID = TYRANNO_UTILS.mod("text_left_image");

	@Getter
	public String title = null;
	public ImageData image;
	public TextData[] text1;
	public TextData[] text2;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int y = getTitleHeight();

		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
		}

		if (this.image != null && this.image.location != null) {
			list.add(new ImageElement(0, y, 50, 50, this.image));
		} else {
			list.add(new ImageElement(0, y, 50, 50, ImageData.MISSING));
		}

		if (this.text1 != null && this.text1.length > 0) {
			list.add(new TextElement(55, y, BookScreen.PAGE_WIDTH - 55, 50, this.text1));
		}

		if (this.text2 != null && this.text2.length > 0) {
			list.add(new TextElement(0, y + 55, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - 55 - y, this.text2));
		}
	}
}
