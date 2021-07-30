package tyrannotitanlib.library.base.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.tags.TyrannoBlockTags;

public class TyrannoBushBlock extends BushBlock
{
	public TyrannoBushBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	protected boolean mayPlaceOn(BlockState state, IBlockReader reader, BlockPos po) 
	{
		Block block = state.getBlock();
		
		return block.is(TyrannoBlockTags.PLANT_PLACEABLE);
	}
}
