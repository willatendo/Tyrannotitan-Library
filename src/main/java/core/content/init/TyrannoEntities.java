package core.content.init;

import static core.content.Util.LOG;

import core.library.tyrannotitanlib.entity.TyrannoBoatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class TyrannoEntities {
	public static final EntityType<TyrannoBoatEntity> BOAT = TyrannoRegistries.create("boat", TyrannoBoatEntity::new, MobCategory.MISC, 1.375F, 0.5625F);

	public static void init() {
		LOG.debug("Registering Tyranno Entities");
	}
}
