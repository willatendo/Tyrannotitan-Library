package tyrannotitanlib.library.block;

import net.minecraft.world.level.block.state.properties.WoodType;

public class TyrannoWoodType extends WoodType {
	public TyrannoWoodType(String id) {
		super(id);
	}

	public static final WoodType CUSTOM = register(new TyrannoWoodType("custom"));
}
