package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import lombok.Getter;
import net.minecraft.Util;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.PageData;
import tyrannotitanlib.library.tyrannobook.data.SectionData;
import tyrannotitanlib.library.tyrannobook.data.element.TextData;
import tyrannotitanlib.library.tyrannobook.screen.book.BookScreen;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.ListingLeftElement;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class ListingContent extends PageContent {
	public static final int LINE_HEIGHT = 10;

	@Getter
	public String title = null;
	public String subText = null;

	private transient final List<List<TextData>> entries = Util.make(() -> {
		List<List<TextData>> lists = new ArrayList<>(1);
		lists.add(new ArrayList<>());
		return lists;
	});

	public void addEntry(String text, @Nullable PageData link, boolean subSection) {
		TextData data = new TextData(text);
		data.bold = subSection;
		if (link != null) {
			data.action = "mantle:go-to-page-rtn " + link.parent.name + "." + link.name;
		}
		this.entries.get(this.entries.size() - 1).add(data);
	}

	public void addEntry(String text, @Nullable PageData link) {
		addEntry(text, link, false);
	}

	public void addColumnBreak() {
		if (!this.entries.get(this.entries.size() - 1).isEmpty()) {
			if (this.entries.size() == 3) {
				TyrannoUtils.LOGGER.warn("Too many columns in content listing, you should create a second listing instead");
			}
			this.entries.add(new ArrayList<>());
		}
	}

	public boolean hasEntries() {
		return this.entries.get(0).size() > 0;
	}

	private static int getColumnHeight(int yOff) {
		int columnHeight = BookScreen.PAGE_HEIGHT - yOff - 16;
		if (columnHeight % LINE_HEIGHT != 0) {
			columnHeight -= columnHeight % LINE_HEIGHT;
		}
		return columnHeight;
	}

	public int getEntriesInColumn(SectionData sectionData) {
		int yOff = 0;
		if (this.title != null) {
			yOff = 16;
		}
		if (this.subText != null) {
			yOff += sectionData.parent.fontRenderer.wordWrapHeight(this.subText, BookScreen.PAGE_WIDTH) * 12 / 9;
		}
		return getColumnHeight(yOff) / LINE_HEIGHT;
	}

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int yOff = 0;
		if (this.title != null) {
			this.addTitle(list, this.title, false);
			yOff = 16;
		}
		if (this.subText != null) {
			int height = this.addText(list, this.subText, false, 0, yOff);
			yOff += height;
		}

		int columnHeight = getColumnHeight(yOff);

		int width = BookScreen.PAGE_WIDTH;
		int finalColumns = this.entries.size();
		int entriesPerColumn = columnHeight / LINE_HEIGHT;
		if (finalColumns < 3) {
			for (List<TextData> column : this.entries) {
				int totalEntries = column.size();
				while (totalEntries > entriesPerColumn) {
					finalColumns++;
					if (finalColumns == 3) {
						break;
					}
					totalEntries -= entriesPerColumn;
				}
			}
		}
		if (finalColumns > 3) {
			finalColumns = 3;
		}
		width /= finalColumns;

		int x = 0;
		int y = 0;
		for (List<TextData> column : this.entries) {
			for (TextData data : column) {
				if (y >= columnHeight) {
					x += width;
					y = 0;
				}
				String text = data.text;
				if (text.isEmpty()) {
					y += LINE_HEIGHT;
				} else {
					if (!data.bold)
						text = "- " + text;
					int height = this.parent.parent.parent.fontRenderer.wordWrapHeight(text, width) * LINE_HEIGHT / 9;
					list.add(new ListingLeftElement(x, y + yOff, width, height, data.bold, data));
					y += height;
				}
			}
			x += width;
			y = 0;
		}
	}
}
