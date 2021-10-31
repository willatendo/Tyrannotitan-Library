package tyrannotitanlib.library.base.block.entity;

import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class TyrannoBarrelBlockEntity extends BarrelTileEntity
{
	@Override
	public TileEntityType<?> getType() 
	{
		return TyrannoBlockEntities.BARREL_BLOCK_ENTITY;
	}
}
