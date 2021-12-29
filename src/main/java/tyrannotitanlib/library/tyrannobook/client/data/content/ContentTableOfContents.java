package tyrannotitanlib.library.tyrannobook.client.data.content;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.client.data.element.TextData;
import tyrannotitanlib.library.tyrannobook.client.data.element.TextElement;
import tyrannotitanlib.library.tyrannobook.client.data.element.TyrannobookElement;
import tyrannotitanlib.library.tyrannobook.client.screen.TyrannobookScreen;

public class ContentTableOfContents extends PageContent {
	public String title;
	public TextData[] data;

	public ContentTableOfContents(String title, TextData... contents) {
		this.title = title;
		this.data = contents;
	}

	@Override
	public void build(TyrannobookData book, ArrayList<TyrannobookElement> list, boolean rightSide) {
		int y = 0;

		if (this.title != null && !this.title.trim().isEmpty()) {
			this.addTitle(list, this.title);
			y += TITLE_HEIGHT;
		}

		for (int i = 0; i < this.data.length; i++) {
			TextData text = this.data[i];
			list.add(new TextElement(0, y + i * (int) (Minecraft.getInstance().font.lineHeight * text.scale), TyrannobookScreen.PAGE_WIDTH, Minecraft.getInstance().font.lineHeight, text));
		}
	}
}
