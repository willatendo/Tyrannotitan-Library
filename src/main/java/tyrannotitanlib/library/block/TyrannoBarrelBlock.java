package tyrannotitanlib.library.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.library.block.entity.TyrannoBarrelBlockEntity;

public class TyrannoBarrelBlock extends BarrelBlock {
	public TyrannoBarrelBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TyrannoBarrelBlockEntity(pos, state);
	}
}
