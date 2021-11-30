package tyrannotitanlib.library.base.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.library.base.block.entity.TyrannoBeehiveBlockEntity;

public class TyrannoBeehiveBlock extends BeehiveBlock
{
	public TyrannoBeehiveBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) 
	{
		return new TyrannoBeehiveBlockEntity(pos, state);
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) 
	{
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) 
	{
		return mirror == Mirror.NONE ? state : state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
}
