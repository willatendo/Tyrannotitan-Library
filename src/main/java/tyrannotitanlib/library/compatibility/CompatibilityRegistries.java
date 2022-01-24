package tyrannotitanlib.library.compatibility;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tyrannotitanlib.content.server.init.TyrannoRegistries;
import tyrannotitanlib.library.compatibility.decorativeblocks.DecorativeBlocksSittingEntity;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;

public class CompatibilityRegistries {
	public static final EntityType<DecorativeBlocksSittingEntity> SITTING = EntityType.Builder.of(DecorativeBlocksSittingEntity::new, MobCategory.MISC).sized(0.0001F, 0.0001F).build("sitting");

	public static void init() {
		if (CompatibilitiesUtils.isDecorativeBlocksLoaded()) {
			TyrannoRegistries.create("sitting", DecorativeBlocksSittingEntity::new, MobCategory.MISC, 0.0001F, 0.0001F);
			TyrannoRegister.registerEntity("sitting", SITTING);
		}
	}
}
