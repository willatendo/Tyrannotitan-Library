package tyrannotitanlib.content.server.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tyrannotitanlib.library.base.entity.TyrannoBoatEntity;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoEntities {
	public static final EntityType<TyrannoBoatEntity> BOAT = TyrannoRegistries.create("boat", TyrannoBoatEntity::new, MobCategory.MISC, 1.375F, 0.5625F);
	// public static final EntityType<DecorativeBlocksDummyEntityForSitting> SITTING = TyrannoRegistries.create("sitting", DecorativeBlocksDummyEntityForSitting::new, MobCategory.MISC, 0.0001F, 0.0001F);

	public static void init() {
		TyrannoUtils.LOGGER.debug("Registering Tyranno Entities");
	}
}
