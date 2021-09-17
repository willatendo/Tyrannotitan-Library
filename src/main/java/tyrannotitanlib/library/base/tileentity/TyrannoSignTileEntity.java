package tyrannotitanlib.library.base.tileentity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;

public class TyrannoSignTileEntity extends SignTileEntity
{
	@Override
	public TileEntityType<?> getType() 
	{
		return TyrannoTileEntities.SIGN_TILE_ENTITY;
	}
}
