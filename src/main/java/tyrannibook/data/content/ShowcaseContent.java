package tyrannibook.data.content;

import static core.content.Util.TYRANNO_UTILS;

import java.util.ArrayList;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import tyrannibook.data.TyrannobookData;
import tyrannibook.data.element.IngredientData;
import tyrannibook.data.element.TextData;
import tyrannibook.screen.book.BookScreen;
import tyrannibook.screen.book.element.BookElement;
import tyrannibook.screen.book.element.ItemElement;
import tyrannibook.screen.book.element.TextElement;

public class ShowcaseContent extends PageContent {
	public static final transient ResourceLocation ID = TYRANNO_UTILS.resource("showcase");

	@Getter
	public String title = null;
	public TextData[] text;
	public IngredientData item;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int y = getTitleHeight();

		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
		}

		if (this.item != null && !this.item.getItems().isEmpty()) {
			ItemElement element = new ItemElement(BookScreen.PAGE_WIDTH / 2 - 15, y, 2.5f, this.item.getItems(), this.item.action);
			list.add(element);
			y += element.height;
		}

		if (this.text != null && this.text.length > 0) {
			list.add(new TextElement(0, y, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - y, this.text));
		}
	}
}
