package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;

import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class BlankContent extends PageContent {
	public static final ResourceLocation ID = TyrannoUtils.rL("blank");

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
	}
}
