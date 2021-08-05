package tyrannotitanlib.library.base.util.registry.registries;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.item.TyrannoBoatItem;
import tyrannotitanlib.library.base.item.TyrannoSpawnEggItem;
import tyrannotitanlib.library.base.util.TyrannoBoatRegistry;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoItemRegister extends AbstractTyrannoRegistry<Item>
{
	public TyrannoItemRegister(TyrannoRegistry registry, DeferredRegister<Item> deferredRegister) 
	{
		super(registry, deferredRegister);
	}
	
	public TyrannoItemRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.ITEMS, registry.getModId()));
	}
	
	public Item build(String id, Item item)
	{
		this.deferredRegister.register(id, () -> item);
		return item;
	}
	
	public Item createSpawnEggItem(String id, Properties properties, NonNullSupplier<EntityType<?>> supplier, int primaryColor, int secondaryColor) 
	{
		return build(id, new TyrannoSpawnEggItem(supplier, primaryColor, secondaryColor, properties));
	}
	
	public Item createBoat(String woodName, Block plank, Properties properties) 
	{
		String type = this.registry.getModId() + ":" + woodName;
		Item boat = build(woodName + "_boat", new TyrannoBoatItem(type, properties.stacksTo(1)));
		TyrannoBoatRegistry.registerBoat(type, boat, plank);
		return boat;
	}
}
