package tyrannotitanlib.test;

import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.library.tyrannobook.client.FileRepository;
import tyrannotitanlib.library.tyrannobook.client.PaddingBookTransformer;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookLoader;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookTransformer;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class BooksTest extends TyrannobookData
{
	private static final ResourceLocation TEST_ID = TyrannoUtils.rL("test");
	public static final TyrannobookData TEST = TyrannobookLoader.registerBook(TEST_ID.toString(), false, false);
	
	public static void initBooks() 
	{	
		addStandardData(TEST, TEST_ID);
	}
	
	private static void addStandardData(TyrannobookData book, ResourceLocation id) 
	{
		book.addRepository(new FileRepository(id.getNamespace() + ":tyrannobook/" + id.getPath()));
		book.addTransformer(TyrannobookTransformer.indexTranformer());
		book.addTransformer(PaddingBookTransformer.INSTANCE);
	}
}