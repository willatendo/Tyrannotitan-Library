package tyrannotitanlib.library.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.library.base.util.interfaces.ITyrannoSign;

public class TyrannoStandingSignBlock extends StandingSignBlock implements ITyrannoSign
{
	public TyrannoStandingSignBlock(Properties properties, WoodType woodType) 
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
