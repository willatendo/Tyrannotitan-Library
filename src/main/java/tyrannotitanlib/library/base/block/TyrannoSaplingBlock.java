package tyrannotitanlib.library.base.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.content.server.tags.TyrannoBlockTags;

public class TyrannoSaplingBlock extends SaplingBlock {
	public TyrannoSaplingBlock(AbstractTreeGrower tree, Properties properties) {
		super(tree, properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter reader, BlockPos po) {
		Block block = state.getBlock();

		return block.defaultBlockState().is(TyrannoBlockTags.PLANT_PLACEABLE);
	}
}
