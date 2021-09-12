package tyrannotitanlib.library.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.library.base.tileentity.TyrannoSignTileEntity;

public class TyrannoStandingSignBlock extends StandingSignBlock implements ITyrannoSign
{
	public TyrannoStandingSignBlock(Properties properties, WoodType woodType) 
	{
		super(properties, woodType);
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) 
	{
		return new TyrannoSignTileEntity();
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return new TyrannoSignTileEntity();
	}
}
