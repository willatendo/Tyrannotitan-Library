package core.library.compatibility;

import core.content.init.TyrannoRegistries;
import core.library.compatibility.decorativeblocks.DecorativeBlocksSittingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tyranniregister.TyrannoRegister;

public class CompatibilityRegistries {
	public static final EntityType<DecorativeBlocksSittingEntity> SITTING = EntityType.Builder.of(DecorativeBlocksSittingEntity::new, MobCategory.MISC).sized(0.0001F, 0.0001F).build("sitting");

	public static void init() {
		if (CompatibilitiesUtils.isDecorativeBlocksLoaded()) {
			TyrannoRegistries.create("sitting", DecorativeBlocksSittingEntity::new, MobCategory.MISC, 0.0001F, 0.0001F);
			TyrannoRegister.registerEntity("sitting", SITTING);
		}
	}
}
