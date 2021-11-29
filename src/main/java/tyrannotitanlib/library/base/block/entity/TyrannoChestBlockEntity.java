package tyrannotitanlib.library.base.block.entity;

import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class TyrannoChestBlockEntity extends ChestTileEntity
{
	protected TyrannoChestBlockEntity(TileEntityType<?> type) 
	{
		super(type);
	}

	public TyrannoChestBlockEntity() 
	{
		super(TyrannoBlockEntities.CHEST_BLOCK_ENTITY);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() 
	{
		return new AxisAlignedBB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 2, worldPosition.getZ() + 2);
	}
}
