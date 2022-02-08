package tyrannibook.data.content;

import static core.content.Util.TYRANNO_UTILS;

import java.util.ArrayList;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import tyrannibook.data.TyrannobookData;
import tyrannibook.data.element.TextData;
import tyrannibook.screen.book.BookScreen;
import tyrannibook.screen.book.element.BookElement;
import tyrannibook.screen.book.element.TextElement;

public class TextContent extends PageContent {
	public static final ResourceLocation ID = TYRANNO_UTILS.resource("text");

	@Getter
	public String title = null;
	public TextData[] text;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int y;
		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
			y = getTitleHeight();
		}
		if (this.text != null && this.text.length > 0) {
			list.add(new TextElement(0, y, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - y, this.text));
		}
	}
}
