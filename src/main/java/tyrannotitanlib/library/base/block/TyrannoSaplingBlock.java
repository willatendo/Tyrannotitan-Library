package tyrannotitanlib.library.base.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.tags.TyrannoBlockTags;

public class TyrannoSaplingBlock extends SaplingBlock
{
	public TyrannoSaplingBlock(final Tree tree, final Properties properties) 
	{
		super(tree, properties);
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader reader, BlockPos po) 
	{
		Block block = state.getBlock();
		
		return block.is(TyrannoBlockTags.PLANT_PLACEABLE);
	}
}
