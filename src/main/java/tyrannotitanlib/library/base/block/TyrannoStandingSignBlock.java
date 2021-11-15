package tyrannotitanlib.library.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.library.base.block.entity.TyrannoSignBlockEntity;

public class TyrannoStandingSignBlock extends StandingSignBlock implements ITyrannoSign
{
	public TyrannoStandingSignBlock(Properties properties, WoodType woodType) 
	{
		super(properties, woodType);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) 
	{
		return true;
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
