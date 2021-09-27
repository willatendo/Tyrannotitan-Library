package tyrannotitanlib.content.server.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import tyrannotitanlib.content.server.util.TyrannoRegistries;
import tyrannotitanlib.library.base.entity.TyrannoBoatEntity;
import tyrannotitanlib.library.base.utils.TyrannoUtils;

public class TyrannoEntities 
{
	public static final EntityType<TyrannoBoatEntity> BOAT = TyrannoRegistries.create("boat", TyrannoBoatEntity::new, EntityClassification.MISC, 1.375F, 0.5625F);
	
	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Entities"); }
}
