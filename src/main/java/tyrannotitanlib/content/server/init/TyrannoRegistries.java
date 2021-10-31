package tyrannotitanlib.content.server.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;

public class TyrannoRegistries 
{	
	protected static <T extends TileEntity> TileEntityType<T> create(String id, TileEntityType<T> tileEntity)
	{
		TyrannoRegister.registerBlockEntity(id, tileEntity);
		return tileEntity;
	}
	
	protected static <T extends Container> ContainerType<T> create(String id, ContainerType<T> container)
	{
		TyrannoRegister.registerContainer(id, container);
		return container;
	}
	
	protected static <T extends Entity> EntityType<T> create(String id, IFactory<T> factory, EntityClassification classifcation, float width, float height)
	{
		EntityType<T> entityType = EntityType.Builder.of(factory, classifcation).sized(width, height).build(id);
		TyrannoRegister.registerEntity(id, entityType);
		return entityType;
	}
	
	public static void register()
	{
		TyrannoBlockEntities.init();
		TyrannoContainers.init();
		TyrannoEntities.init();
	}
}
