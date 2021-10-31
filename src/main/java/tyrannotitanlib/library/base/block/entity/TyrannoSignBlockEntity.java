package tyrannotitanlib.library.base.block.entity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class TyrannoSignBlockEntity extends SignTileEntity
{
	@Override
	public TileEntityType<?> getType() 
	{
		return TyrannoBlockEntities.SIGN_BLOCK_ENTITY;
	}
}
