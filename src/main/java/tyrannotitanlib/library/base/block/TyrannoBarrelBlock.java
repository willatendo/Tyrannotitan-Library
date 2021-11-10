package tyrannotitanlib.library.base.block;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.library.base.block.entity.TyrannoBarrelBlockEntity;

public class TyrannoBarrelBlock extends BarrelBlock
{
	public TyrannoBarrelBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) 
	{
		return new TyrannoBarrelBlockEntity();
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return TyrannoBlockEntities.BARREL_BLOCK_ENTITY.create();
	}
}
