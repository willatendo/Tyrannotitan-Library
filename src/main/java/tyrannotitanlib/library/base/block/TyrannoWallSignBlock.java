package tyrannotitanlib.library.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.library.base.util.interfaces.ITyrannoSign;

public class TyrannoWallSignBlock extends WallSignBlock implements ITyrannoSign
{
	public TyrannoWallSignBlock(Properties properties, WoodType woodType) 
	{
		super(properties, woodType);
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) 
	{
		return super.newBlockEntity(reader);
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return super.createTileEntity(state, world);
	}
}
