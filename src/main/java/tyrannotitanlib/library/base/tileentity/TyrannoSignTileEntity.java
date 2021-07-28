package tyrannotitanlib.library.base.tileentity;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;

/*
 * This simply makes it so we can add to the SignTileEntites's affected blocks
 * 
 * ---
 * 
 * Author: Willatendo
 * 
 * Creation Date: July 24, 2021
 * Final Edit Date: July 24, 2021
 */

public class TyrannoSignTileEntity extends SignTileEntity
{
	@Override
	public TileEntityType<?> getType() 
	{
		return TyrannoTileEntities.SIGN.get();
	}
}
