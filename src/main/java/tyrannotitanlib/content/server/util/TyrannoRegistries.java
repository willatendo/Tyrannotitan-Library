package tyrannotitanlib.content.server.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoEntities;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;

public class TyrannoRegistries 
{	
	public static <T extends TileEntity> TileEntityType<T> create(String id, TileEntityType<T> tileEntity)
	{
		TyrannoRegister.registerBlockEntity(id, tileEntity);
		return tileEntity;
	}
	
	public static <T extends Entity> EntityType<T> create(String id, IFactory<T> factory, EntityClassification classifcation, float width, float height)
	{
		EntityType<T> entityType = EntityType.Builder.of(factory, classifcation).sized(width, height).build(id);
		TyrannoRegister.registerEntity(id, entityType);
		return entityType;
	}
	
	public static void register()
	{
		TyrannoTileEntities.init();
		TyrannoEntities.init();
	}
}
