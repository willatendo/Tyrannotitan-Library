package tyrannotitanlib.library.base.block.entity;

import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;

public class TyrannoBeehiveBlockEntity extends BeehiveTileEntity
{
	@Override
	public TileEntityType<?> getType() 
	{
		return TyrannoBlockEntities.BEEHIVE_BLOCK_ENTITY;
	}
}
