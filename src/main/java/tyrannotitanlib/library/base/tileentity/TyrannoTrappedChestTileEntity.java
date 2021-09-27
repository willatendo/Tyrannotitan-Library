package tyrannotitanlib.library.base.tileentity;

import tyrannotitanlib.content.server.init.TyrannoTileEntities;

public class TyrannoTrappedChestTileEntity extends TyrannoChestTileEntity
{
	public TyrannoTrappedChestTileEntity() 
	{
		super(TyrannoTileEntities.TRAPPED_CHEST_TILE_ENTITY);
	}

	@Override
	protected void signalOpenCount() 
	{
		super.signalOpenCount();
		this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
	}
}
