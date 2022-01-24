package tyrannotitanlib.library.tyrannotitanlib.block;

import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TyrannoStandingSignBlock extends StandingSignBlock {
	public TyrannoStandingSignBlock(Properties properties, WoodType woodType) {
		super(properties, woodType);

		TyrannoSignManager.registerSignBlock(() -> this);
	}
}
