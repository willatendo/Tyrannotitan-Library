package tyrannotitanlib.test;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tyrannotitanlib.library.tyrannobook.client.FileRepository;
import tyrannotitanlib.library.tyrannobook.client.PaddingBookTransformer;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookLoader;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookTransformer;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.item.TyrannobookItem;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;
import tyrannotitanlib.library.utils.TyrannoUtils;

@EventBusSubscriber(modid = TyrannoUtils.TYRANNO_ID, bus = Bus.MOD)
public class Book 
{
	public static final Item BOOK = register("book", new TyrannobookItem(new Properties().tab(CreativeModeTab.TAB_MISC))
	{
		public InteractionResultHolder<ItemStack> use(Level level, Player entity, InteractionHand hand) 
		{
			ItemStack stack = entity.getItemInHand(hand);
			if(level.isClientSide)
			{
				Books.TEST.openGui(hand, stack);
			}
			return InteractionResultHolder.success(stack);
		};
		
		@Override
		public void openLecternScreenClient(BlockPos pos, ItemStack stack) 
		{
			Books.TEST.openGui(pos, stack);
		}
	});
	
	public static Item register(String id, Item item)
	{
		TyrannoRegister.registerItem(id, item);
		return item;
	}
	
	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) 
	{
		Books.initBooks();
	}
	
	public static class Books extends TyrannobookData
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
}
