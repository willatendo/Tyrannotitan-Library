package tyrannotitanlib.library.base.block.entity;

import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class TyrannoTrappedChestBlockEntity extends TyrannoChestBlockEntity
{
	public TyrannoTrappedChestBlockEntity() 
	{
		super(TyrannoBlockEntities.TRAPPED_CHEST_BLOCK_ENTITY);
	}

	@Override
	protected void signalOpenCount() 
	{
		super.signalOpenCount();
		this.level.updateNeighborsAt(this.worldPosition.below(), this.getBlockState().getBlock());
	}
}
