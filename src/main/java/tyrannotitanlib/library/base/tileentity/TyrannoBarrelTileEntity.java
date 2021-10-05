package tyrannotitanlib.library.base.tileentity;

import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;

public class TyrannoBarrelTileEntity extends BarrelTileEntity
{
	@Override
	public TileEntityType<?> getType() 
	{
		return TyrannoTileEntities.BARREL_TILE_ENTITY;
	}
}
