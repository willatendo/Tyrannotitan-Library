package tyrannotitanlib.library.base.block;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class TyrannoBeehiveBlock extends BeehiveBlock
{
	public TyrannoBeehiveBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return TyrannoBlockEntities.BEEHIVE_BLOCK_ENTITY.create();
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return mirrorIn == Mirror.NONE ? state : state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
}
