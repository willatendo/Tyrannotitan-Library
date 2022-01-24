package tyrannotitanlib.content.server.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tyrannotitanlib.content.TyrannoUtils;
import tyrannotitanlib.library.tyrannotitanlib.entity.TyrannoBoatEntity;

public class TyrannoEntities {
	public static final EntityType<TyrannoBoatEntity> BOAT = TyrannoRegistries.create("boat", TyrannoBoatEntity::new, MobCategory.MISC, 1.375F, 0.5625F);

	public static void init() {
		TyrannoUtils.LOGGER.debug("Registering Tyranno Entities");
	}
}
