package tyrannotitanlib.content.server.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.block.ITyrannoSign;
import tyrannotitanlib.library.base.tileentity.TyrannoSignTileEntity;
import tyrannotitanlib.library.base.util.TyrannoUtils;

public class TyrannoTileEntities 
{	
	public static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TyrannoUtils.TYRANNO_ID);
	
	public static final RegistryObject<TileEntityType<TyrannoSignTileEntity>> SIGN = REGISTER.register("sign", () -> TileEntityType.Builder.of(TyrannoSignTileEntity::new, collectBlocks(ITyrannoSign.class)).build(null));	

	public static Block[] collectBlocks(Class<?> blockClass) 
	{
		return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
	}
	public static void init() { }
}
