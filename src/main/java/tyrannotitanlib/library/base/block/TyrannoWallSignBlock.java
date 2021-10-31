package tyrannotitanlib.library.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.library.base.block.entity.TyrannoSignBlockEntity;

public class TyrannoWallSignBlock extends WallSignBlock implements ITyrannoSign
{
	public TyrannoWallSignBlock(Properties properties, WoodType woodType) 
	{
		super(properties, woodType);
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) 
	{
		return new TyrannoSignBlockEntity();
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return TyrannoBlockEntities.SIGN_BLOCK_ENTITY.create();
	}
}
