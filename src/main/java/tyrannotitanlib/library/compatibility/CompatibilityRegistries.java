package tyrannotitanlib.library.compatibility;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tyrannotitanlib.library.compatibility.decorativeblocks.DecorativeBlocksSittingEntity;
import tyrannotitanlib.tyranniregister.TyrannoRegister;

public class CompatibilityRegistries {
	public static final EntityType<DecorativeBlocksSittingEntity> SITTING = EntityType.Builder.of(DecorativeBlocksSittingEntity::new, MobCategory.MISC).sized(0.0001F, 0.0001F).build("sitting");

	public static void init() {
		if (CompatibilitiesUtils.isDecorativeBlocksLoaded()) {
			TyrannoRegister.registerEntity("sitting", SITTING);
		}
	}
}
