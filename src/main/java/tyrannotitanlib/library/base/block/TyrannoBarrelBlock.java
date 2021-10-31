package tyrannotitanlib.library.base.block;

import net.minecraft.block.BarrelBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
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
}