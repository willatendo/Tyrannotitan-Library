package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;

import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.SectionData;
import tyrannotitanlib.library.tyrannobook.screen.book.BookScreen;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.SelectionElement;

public class SectionListContent extends PageContent {
	protected ArrayList<SectionData> sections = new ArrayList<>();

	public boolean addSection(SectionData data) {
		return this.sections.size() < 12 && this.sections.add(data);
	}

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int columns = book.appearance.drawFourColumnIndex ? 4 : 3;
		int width = (SelectionElement.WIDTH + 5) * columns - 5;
		int height = (SelectionElement.HEIGHT + 5) * 3 - 5;

		int ox = (BookScreen.PAGE_WIDTH - width) / 2;
		int oy = (BookScreen.PAGE_HEIGHT - height) / 2 - 5;

		int sectionRange = Math.min(columns * 3, this.sections.size());
		for (int i = 0; i < sectionRange; i++) {
			int ix = i % columns;
			int iy = i / columns;

			int x = ox + ix * (SelectionElement.WIDTH + 5);
			int y = oy + iy * (SelectionElement.HEIGHT + 5);

			list.add(new SelectionElement(x, y, this.sections.get(i)));
		}
	}
}
