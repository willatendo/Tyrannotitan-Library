package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.Util.LOG;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tyrannotitanlib.library.entity.TyrannoBoatEntity;
import tyrannotitanlib.tyranniregister.TyrannoRegister;

public class TyrannoEntities {
	public static final EntityType<TyrannoBoatEntity> BOAT = TyrannoRegister.registerEntity("boat", TyrannoBoatEntity::new, MobCategory.MISC, 1.375F, 0.5625F);

	public static void init() {
		LOG.debug("Registering Tyranno Entities");
	}
}
