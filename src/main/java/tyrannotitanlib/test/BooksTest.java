package tyrannotitanlib.test;

import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.library.tyrannobook.TyrannobookLoader;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.repository.FileRepository;
import tyrannotitanlib.library.tyrannobook.transformer.TyrannobookTransformer;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class BooksTest extends TyrannobookData {
	private static final ResourceLocation TEST_ID = TyrannoUtils.rL("test");
	public static final TyrannobookData TEST = TyrannobookLoader.registerBook(TEST_ID, false, false);

	public static void initBooks() {
		addStandardData(TEST, TEST_ID);
	}

	private static void addStandardData(TyrannobookData book, ResourceLocation id) {
		book.addRepository(new FileRepository(TyrannoUtils.rL("tyrannobook/" + id.getPath())));
		book.addTransformer(TyrannobookTransformer.indexTranformer());
		book.addTransformer(TyrannobookTransformer.paddingTransformer());
	}
}