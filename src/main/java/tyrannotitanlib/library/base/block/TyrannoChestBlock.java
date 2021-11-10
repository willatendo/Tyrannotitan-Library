package tyrannotitanlib.library.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.library.base.block.entity.TyrannoChestBlockEntity;

public class TyrannoChestBlock extends ChestBlock implements ITyrannoChestBlock
{
	private final String type;
	
	public TyrannoChestBlock(String type, Properties properties) 
	{
		super(properties, () -> TyrannoBlockEntities.CHEST_BLOCK_ENTITY);
		this.type = type;
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) 
	{
		return new TyrannoChestBlockEntity();
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return TyrannoBlockEntities.CHEST_BLOCK_ENTITY.create();
	}

	@Override
	public String getChestType() 
	{
		return this.type;
	}
}
