package tyrannotitanlib.content.server.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.content.server.init.TyrannoEntities;
import tyrannotitanlib.content.server.init.TyrannoItems;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;

public class TyrannoRegistries 
{
	public static Item create(String id, Item item)
	{
		TyrannoRegister.registerItem(id, item);
		return item;
	}
	
	public static Block create(String id, Block block)
	{
		TyrannoRegister.registerBlock(id, block);
		return block;
	}
	
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
		TyrannoItems.init();
		TyrannoTileEntities.init();
		TyrannoEntities.init();
	}
}
