package tyrannibook.data.content;

import static core.content.Util.TYRANNO_UTILS;

import java.util.ArrayList;

import net.minecraft.resources.ResourceLocation;
import tyrannibook.data.TyrannobookData;
import tyrannibook.screen.book.element.BookElement;

public class BlankContent extends PageContent {
	public static final ResourceLocation ID = TYRANNO_UTILS.resource("blank");

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
	}
}
