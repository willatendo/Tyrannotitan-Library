package tyrannotitanlib.library.block;

import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class TyrannoWallSignBlock extends WallSignBlock {
	public TyrannoWallSignBlock(Properties properties, WoodType woodType) {
		super(properties, woodType);

		TyrannoSignManager.registerSignBlock(() -> this);
	}
}
