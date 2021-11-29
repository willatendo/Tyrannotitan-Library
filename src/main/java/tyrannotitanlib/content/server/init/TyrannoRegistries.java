package tyrannotitanlib.content.server.init;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityType;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;

public class TyrannoRegistries 
{	
	public static BannerPattern create(String id)
	{
		return TyrannoRegister.registerPattern(id);
	}
	
	public static Item create(String id, Item item)
	{
		TyrannoRegister.registerItem(id, item);
		return item;
	}
	
	public static Block create(String id, Block block)
	{
		BlockItem item = new BlockItem(block, new Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS));
		TyrannoRegister.registerItem(id, item);
		TyrannoRegister.registerBlock(id, block);
		return block;
	}
	
	public static TileEntityType create(String id, TileEntityType blockEntity)
	{
		TyrannoRegister.registerBlockEntity(id, blockEntity);
		return blockEntity;
	}
	
	public static ContainerType create(String id, ContainerType container)
	{
		TyrannoRegister.registerContainer(id, container);
		return container;
	}
	
	public static EntityType create(String id, IFactory factory, EntityClassification classifcation, float width, float height)
	{
		EntityType entityType = EntityType.Builder.of(factory, classifcation).sized(width, height).build(id);
		TyrannoRegister.registerEntity(id, entityType);
		return entityType;
	}
	
	public static void register()
	{
		TyrannoBanners.init();
		TyrannoBlocks.init();
		TyrannoBlockEntities.init();
		TyrannoContainers.init();
		TyrannoItems.init();
		TyrannoEntities.init();
	}
}
