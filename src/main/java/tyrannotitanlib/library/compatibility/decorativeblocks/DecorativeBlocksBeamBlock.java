package tyrannotitanlib.library.compatibility.decorativeblocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

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
	public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return woodType.isFlammable();
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		if (woodType.isFlammable()) {
			return 20;
		} else
			return super.getFlammability(state, world, pos, face);
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		if (woodType.isFlammable()) {
			return 5;
		} else
			return super.getFireSpreadSpeed(state, world, pos, face);
	}

}
