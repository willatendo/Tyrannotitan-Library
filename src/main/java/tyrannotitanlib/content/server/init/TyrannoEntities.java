package tyrannotitanlib.content.server.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import tyrannotitanlib.content.TyrannotitanLibrary.Registry;
import tyrannotitanlib.library.base.entity.TyrannoBoatEntity;

public class TyrannoEntities 
{
	public static final EntityType<TyrannoBoatEntity> BOAT = Registry.create("boat", TyrannoBoatEntity::new, EntityClassification.MISC, 1.375F, 0.5625F);

	public static void init() { }
}
