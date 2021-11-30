package tyrannotitanlib.content.server.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
		BlockItem item = new BlockItem(block, new Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
		TyrannoRegister.registerItem(id, item);
		TyrannoRegister.registerBlock(id, block);
		return block;
	}
	
	public static BlockEntityType create(String id, BlockEntityType blockEntity)
	{
		TyrannoRegister.registerBlockEntity(id, blockEntity);
		return blockEntity;
	}
	
	public static MenuType create(String id, MenuType container)
	{
		TyrannoRegister.registerContainer(id, container);
		return container;
	}
	
	public static EntityType create(String id, EntityFactory factory, MobCategory classifcation, float width, float height)
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
