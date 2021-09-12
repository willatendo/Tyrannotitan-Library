package tyrannotitanlib.content;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.content.server.init.TyrannoEntities;
import tyrannotitanlib.content.server.init.TyrannoItems;
import tyrannotitanlib.content.server.init.TyrannoTileEntities;
import tyrannotitanlib.library.base.util.TyrannoUtils;

@Mod(TyrannoUtils.TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public TyrannotitanLibrary() 
	{
		Registry.register();
	}
	
	public static class Registry
	{
		public static Item create(String id, Item item)
		{
			item.setRegistryName(new ResourceLocation(TyrannoUtils.TYRANNO_ID, id));
			ForgeRegistries.ITEMS.register(item);
			return item;
		}
		public static <T extends TileEntity> TileEntityType<T> create(String id, TileEntityType<T> tileEntity)
		{
			tileEntity.setRegistryName(new ResourceLocation(TyrannoUtils.TYRANNO_ID, id));
			ForgeRegistries.TILE_ENTITIES.register(tileEntity);
			return tileEntity;
		}
		
		public static <T extends Entity> EntityType<T> create(String id, IFactory<T> factory, EntityClassification classifcation, float width, float height)
		{
			EntityType<T> entityType = EntityType.Builder.of(factory, classifcation).sized(width, height).build(id);
			entityType.setRegistryName(new ResourceLocation(TyrannoUtils.TYRANNO_ID, id));
			ForgeRegistries.ENTITIES.register(entityType);
			return entityType;
		}
		
		public static void register()
		{
			TyrannoItems.init();
			TyrannoTileEntities.init();
			TyrannoEntities.init();
		}
	}
}
