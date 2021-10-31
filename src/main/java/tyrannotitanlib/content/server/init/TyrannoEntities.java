package tyrannotitanlib.content.server.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import tyrannotitanlib.library.base.entity.TyrannoBoatEntity;
import tyrannotitanlib.library.compatibility.decorativeblocks.DecorativeBlocksDummyEntityForSitting;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoEntities 
{
	public static final EntityType<TyrannoBoatEntity> BOAT = TyrannoRegistries.create("boat", TyrannoBoatEntity::new, EntityClassification.MISC, 1.375F, 0.5625F);
	public static final EntityType<DecorativeBlocksDummyEntityForSitting> SITTING = TyrannoRegistries.create("sitting", DecorativeBlocksDummyEntityForSitting::new, EntityClassification.MISC, 0.0001F, 0.0001F);
	
	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Entities"); }
}
