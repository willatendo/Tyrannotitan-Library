package core.library.tyrannotitanlib.block;

import core.library.tyrannotitanlib.block.entity.TyrannoBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TyrannoBarrelBlock extends BarrelBlock {
	public TyrannoBarrelBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TyrannoBarrelBlockEntity(pos, state);
	}
}
