package tyrannotitanlib.library.base.tileentity;

import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;

public class TyrannoBeehiveTileEntity extends BeehiveTileEntity
{
	public TyrannoBeehiveTileEntity() 
	{
		super();
	}

	@Override
	public TileEntityType<?> getType() 
	{
		return TyrannoTileEntities.BEEHIVE_TILE_ENTITY;
	}
}
