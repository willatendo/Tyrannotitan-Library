package tyrannotitanlib.library.tyrannobook.client.data.content;

import java.util.ArrayList;

import tyrannotitanlib.library.tyrannobook.client.data.SectionData;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.client.data.element.SelectionElement;
import tyrannotitanlib.library.tyrannobook.client.data.element.TyrannobookElement;
import tyrannotitanlib.library.tyrannobook.client.screen.TyrannobookScreen;

public class ContentSectionList extends PageContent {
	protected ArrayList<SectionData> sections = new ArrayList<>();

	public boolean addSection(SectionData data) {
		return this.sections.size() < 9 && this.sections.add(data);
	}

	@Override
	public void build(TyrannobookData book, ArrayList<TyrannobookElement> list, boolean rightSide) {
		int width = (SelectionElement.WIDTH + 5) * 3 - 5;
		int height = (SelectionElement.HEIGHT + 5) * 3 - 5;

		int ox = (TyrannobookScreen.PAGE_WIDTH - width) / 2;
		int oy = (TyrannobookScreen.PAGE_HEIGHT - height) / 2;

		for (int i = 0; i < this.sections.size(); i++) {
			int ix = i % 3;
			int iy = (int) Math.floor(i / 3F);

			int x = ox + ix * (SelectionElement.WIDTH + 5);
			int y = oy + iy * (SelectionElement.HEIGHT + 5);

			list.add(new SelectionElement(x, y, this.sections.get(i)));
		}
	}
}
