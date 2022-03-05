package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.Util.LOG;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;
import tyrannotitanlib.library.entity.TyrannoBoatEntity;
import tyrannotitanlib.tyranniregister.TyrannoRegister;

public class TyrannoEntities {
	public static final RegistryObject<EntityType<TyrannoBoatEntity>> TYRANNO_BOAT = TyrannoRegister.ENTITIES.register("tyranno_boat", () -> EntityType.Builder.<TyrannoBoatEntity>of(TyrannoBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build("tyranno_boat"));

	public static void init() {
		LOG.debug("Registering Tyranno Entities");
	}
}
