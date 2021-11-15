package tyrannotitanlib.library.base.block;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.library.base.block.entity.TyrannoBeehiveBlockEntity;

public class TyrannoBeehiveBlock extends BeehiveBlock
{
	public TyrannoBeehiveBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) 
	{
		return true;
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) 
	{
		return new TyrannoBeehiveBlockEntity();
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return TyrannoBlockEntities.BEEHIVE_BLOCK_ENTITY.create();
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
