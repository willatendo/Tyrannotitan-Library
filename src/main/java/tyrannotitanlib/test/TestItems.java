package tyrannotitanlib.test;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tyrannotitanlib.library.tyrannobook.item.TyrannobookItem;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;
import tyrannotitanlib.library.utils.TyrannoUtils;

@EventBusSubscriber(modid = TyrannoUtils.TYRANNO_ID, bus = Bus.MOD)
public class TestItems 
{
	public static final Item BOOK = register("book", new TyrannobookItem(new Properties().tab(CreativeModeTab.TAB_MISC))
	{
		@Override
		public InteractionResultHolder<ItemStack> use(Level level, Player entity, InteractionHand hand) 
		{
			ItemStack stack = entity.getItemInHand(hand);
			if(level.isClientSide)
			{
				BooksTest.TEST.openGui(hand, stack);
			}
			return InteractionResultHolder.success(stack);
		};
		
		@Override
		public void openLecternScreenClient(BlockPos pos, ItemStack stack) 
		{
			BooksTest.TEST.openGui(pos, stack);
		}
	});
	
	public static Item register(String id, Item item)
	{
		TyrannoRegister.registerItem(id, item);
		return item;
	}
}
