package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.element.TextData;
import tyrannotitanlib.library.tyrannobook.screen.book.BookScreen;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.TextElement;

public class TableOfContentsContent extends PageContent {
	@Getter
	public String title;
	public TextData[] data;

	public TableOfContentsContent(String title, TextData... contents) {
		this.title = title;
		this.data = contents;
	}

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int y = 0;

		if (this.title != null && !this.title.trim().isEmpty()) {
			this.addTitle(list, this.title);
			y += getTitleHeight();
		}

		for (int i = 0; i < this.data.length; i++) {
			TextData text = this.data[i];
			list.add(new TextElement(0, y + i * (int) (Minecraft.getInstance().font.lineHeight * text.scale), BookScreen.PAGE_WIDTH, Minecraft.getInstance().font.lineHeight, text));
		}
	}
}
