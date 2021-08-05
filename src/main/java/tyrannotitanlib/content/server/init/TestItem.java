package tyrannotitanlib.content.server.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tyrannotitanlib.content.TyrannotitanLibrary;
import tyrannotitanlib.library.base.item.debug.DebugItem;
import tyrannotitanlib.library.base.util.TyrannoUtils;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoItemRegister;

@EventBusSubscriber(modid = TyrannoUtils.TYRANNO_ID, bus = Bus.MOD)
public class TestItem 
{
	private static final TyrannoItemRegister HELPER = TyrannotitanLibrary.REGISTRY.itemHelper();

	public static final Item ITEMS = HELPER.build("item", DebugItem.create());
}
