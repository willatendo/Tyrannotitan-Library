package tyrannotitanlib.core.content.init;

import static tyrannotitanlib.core.content.ModUtilities.LOG;

import com.tterrag.registrate.util.entry.EntityEntry;

import net.minecraft.world.entity.MobCategory;
import tyrannotitanlib.TyrannotitanLibrary;
import tyrannotitanlib.library.TyrannoRegistrate;
import tyrannotitanlib.library.client.TyrannoBoatRenderer;
import tyrannotitanlib.library.entity.TyrannoBoatEntity;

public class TyrannoEntities {
	public static final TyrannoRegistrate REGISTRATE = TyrannotitanLibrary.CENTRAL_REGISTRATE.get();

	public static final EntityEntry<TyrannoBoatEntity> TYRANNO_BOAT = REGISTRATE.<TyrannoBoatEntity>entity("tyranno_boat", TyrannoBoatEntity::new, MobCategory.MISC).renderer(() -> TyrannoBoatRenderer::new).lang("Boat").register();
			//, MobCategory.MISC, 1.375F, 0.5625F).s;

	public static void init() {
		LOG.debug("Registering Tyranno Entities");
	}
}
