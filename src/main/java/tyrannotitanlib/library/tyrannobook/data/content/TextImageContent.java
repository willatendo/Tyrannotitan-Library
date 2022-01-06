package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.element.ImageData;
import tyrannotitanlib.library.tyrannobook.data.element.TextData;
import tyrannotitanlib.library.tyrannobook.screen.book.BookScreen;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.ImageElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.TextElement;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TextImageContent extends PageContent {
	public static final ResourceLocation ID = TyrannoUtils.rL("text_image");

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
