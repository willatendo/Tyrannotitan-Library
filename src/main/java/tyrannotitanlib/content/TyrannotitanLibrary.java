package tyrannotitanlib.content;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.IFactory;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import tyrannotitanlib.content.server.init.TyrannoEntities;
import tyrannotitanlib.content.server.init.TyrannoItems;
import tyrannotitanlib.library.base.util.TyrannoUtils;
import tyrannotitanlib.library.base.util.registry.SimpleRegisters;

@Mod(TyrannoUtils.TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public TyrannotitanLibrary() { }
	
	public static class Registry
	{
		public static Item create(String id, Item item)
		{
			return SimpleRegisters.register(TyrannoUtils.TYRANNO_ID, id, item);
		}
		
		public static <T extends Entity> EntityType<T> create(String id, IFactory<T> factory, EntityClassification classifcation, float width, float height)
		{
			return SimpleRegisters.register(id, id, factory, classifcation, width, height);
		}
		
		public static void register()
		{
			TyrannoItems.init();
			TyrannoEntities.init();
		}
	}
}
