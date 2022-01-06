package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.element.ImageData;
import tyrannotitanlib.library.tyrannobook.screen.book.BookScreen;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.ImageElement;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class ImageContent extends PageContent {
	public static final ResourceLocation ID = TyrannoUtils.rL("image");

	@Getter
	public String title = null;
	public ImageData image;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		int y = getTitleHeight();

		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
		}

		if (this.image != null && this.image.location != null) {
			list.add(new ImageElement(0, y, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - y, this.image));
		} else {
			list.add(new ImageElement(ImageData.MISSING));
		}
	}
}
