package tyrannotitanlib.content.server.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tyrannotitanlib.content.TyrannotitanLibrary;
import tyrannotitanlib.library.base.entity.TyrannoBoatEntity;
import tyrannotitanlib.library.base.util.TyrannoUtils;
import tyrannotitanlib.library.base.util.registry.registries.TyrannoEntityRegister;

@EventBusSubscriber(modid = TyrannoUtils.TYRANNO_ID, bus = Bus.MOD)
public class TyrannoEntities 
{
	private static final TyrannoEntityRegister HELPER = TyrannotitanLibrary.REGISTRY.entityHelper();

	public static final RegistryObject<EntityType<TyrannoBoatEntity>> BOAT = HELPER.build("boat", TyrannoBoatEntity::new, EntityClassification.MISC, TyrannoBoatEntity.class, 1.375F, 0.5625F);
}
