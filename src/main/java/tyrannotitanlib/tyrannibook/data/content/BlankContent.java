package tyrannotitanlib.tyrannibook.data.content;

import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

import java.util.ArrayList;

import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.tyrannibook.data.TyrannobookData;
import tyrannotitanlib.tyrannibook.screen.book.element.BookElement;

public class BlankContent extends PageContent {
	public static final ResourceLocation ID = TYRANNO_UTILS.mod("blank");

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
	}
}
