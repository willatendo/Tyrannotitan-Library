package core.library.compatibility.decorativeblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DecorativeBlocksBeamBlock extends RotatedPillarBlock {
	private IDecorativeBlocksWoodType woodType;

	public DecorativeBlocksBeamBlock(Block.Properties properties, IDecorativeBlocksWoodType woodType) {
		super(properties);
		this.woodType = woodType;
	}

	public IDecorativeBlocksWoodType getWoodType() {
		return woodType;
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return woodType.isFlammable();
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		if (woodType.isFlammable()) {
			return 20;
		} else {
			return super.getFlammability(state, world, pos, face);
		}
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		if (woodType.isFlammable()) {
			return 5;
		} else {
			return super.getFireSpreadSpeed(state, world, pos, face);
		}
	}

}
