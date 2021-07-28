package tyrannotitanlib.content.server.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tyrannotitanlib.content.TyrannotitanLibrary;
import tyrannotitanlib.library.base.tileentity.TyrannoSignTileEntity;
import tyrannotitanlib.library.base.util.TyrannoUtils;
import tyrannotitanlib.library.base.util.interfaces.ITyrannoSign;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoTileEntityRegister;

@EventBusSubscriber(modid = TyrannoUtils.TYRANNO_ID, bus = Bus.MOD)
public class TyrannoTileEntities 
{
	private static final TyrannoTileEntityRegister REGISTRY = TyrannotitanLibrary.REGISTRY.tileEntityHelper();
	
	public static final RegistryObject<TileEntityType<TyrannoSignTileEntity>> SIGN = REGISTRY.build("sign", TyrannoSignTileEntity::new, () -> TyrannoTileEntityRegister.collectBlocks(ITyrannoSign.class));
}
