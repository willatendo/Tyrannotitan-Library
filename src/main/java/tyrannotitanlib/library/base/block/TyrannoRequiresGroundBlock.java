package tyrannotitanlib.library.base.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

/*
 * This simply makes it so the block requires ground inorder to be placed.
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

public class TyrannoRequiresGroundBlock extends Block
{
	public TyrannoRequiresGroundBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos newPos) 
	{
		return Direction.DOWN == direction && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, newState, world, pos, newPos);
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader reader, BlockPos pos) 
	{
		BlockState blockstate = reader.getBlockState(pos.below());
		return blockstate.is(Blocks.AIR) ? false : blockstate.is(Blocks.WATER) ? false : blockstate.is(Blocks.LAVA) ? false : true;
	}
}
