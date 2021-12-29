package tyrannotitanlib.library.tyrannobook.client.data.content;

import java.util.ArrayList;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.client.data.element.ImageData;
import tyrannotitanlib.library.tyrannobook.client.data.element.ImageElement;
import tyrannotitanlib.library.tyrannobook.client.data.element.TextData;
import tyrannotitanlib.library.tyrannobook.client.data.element.TextElement;
import tyrannotitanlib.library.tyrannobook.client.data.element.TyrannobookElement;
import tyrannotitanlib.library.tyrannobook.client.screen.TyrannobookScreen;

@OnlyIn(Dist.CLIENT)
public class ContentTextRightImage extends PageContent {
	public String title;
	public TextData[] text1;
	public TextData[] text2;
	public ImageData image;

	@Override
	public void build(TyrannobookData book, ArrayList<TyrannobookElement> list, boolean rightSide) {
		int y = TITLE_HEIGHT;

		if (this.title == null || this.title.isEmpty()) {
			y = 0;
		} else {
			this.addTitle(list, this.title);
		}

		if (this.text1 != null && this.text1.length > 0) {
			list.add(new TextElement(0, y, TyrannobookScreen.PAGE_WIDTH - 55, 50, this.text1));
		}

		if (this.image != null && this.image.location != null) {
			list.add(new ImageElement(TyrannobookScreen.PAGE_WIDTH - 50, y, 50, 50, this.image));
		} else {
			list.add(new ImageElement(TyrannobookScreen.PAGE_WIDTH - 50, y, 50, 50, ImageData.MISSING));
		}

		if (this.text2 != null && this.text2.length > 0) {
			list.add(new TextElement(0, y + 55, TyrannobookScreen.PAGE_WIDTH, TyrannobookScreen.PAGE_HEIGHT - 55 - y, this.text2));
		}
	}
}
