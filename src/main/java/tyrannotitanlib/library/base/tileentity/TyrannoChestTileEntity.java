package tyrannotitanlib.library.base.tileentity;

import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;

public class TyrannoChestTileEntity extends ChestTileEntity
{
	protected TyrannoChestTileEntity(TileEntityType<?> typeIn) 
	{
		super(typeIn);
	}

	public TyrannoChestTileEntity() 
	{
		super(TyrannoTileEntities.CHEST_TILE_ENTITY);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() 
	{
		return new AxisAlignedBB(worldPosition.getX() - 1, worldPosition.getY(), worldPosition.getZ() - 1, worldPosition.getX() + 2, worldPosition.getY() + 2, worldPosition.getZ() + 2);
	}
}
