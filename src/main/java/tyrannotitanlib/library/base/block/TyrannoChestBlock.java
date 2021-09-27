package tyrannotitanlib.library.base.block;

import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;
import tyrannotitanlib.library.base.tileentity.TyrannoChestTileEntity;

public class TyrannoChestBlock extends ChestBlock implements ITyrannoChestBlock
{
	private final String type;
	
	public TyrannoChestBlock(String type, Properties properties) 
	{
		super(properties, () -> TyrannoTileEntities.CHEST_TILE_ENTITY);
		this.type = type;
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) 
	{
		return new TyrannoChestTileEntity();
	}

	@Override
	public String getChestType() 
	{
		return this.type;
	}
}
