package tyrannotitanlib.library.base.util.registry.registries;

import com.mojang.datafixers.util.Pair;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.SignItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tyrannotitanlib.library.base.block.TyrannoStandingSignBlock;
import tyrannotitanlib.library.base.block.TyrannoWallSignBlock;
import tyrannotitanlib.library.base.util.TyrannoSignManager;
import tyrannotitanlib.library.base.util.registry.AbstractTyrannoRegistry;
import tyrannotitanlib.library.base.util.registry.TyrannoRegistry;

public class TyrannoBlockRegister extends AbstractTyrannoRegistry<Block>
{
	
	public TyrannoBlockRegister(TyrannoRegistry registry, DeferredRegister<Block> deferredRegister) 
	{
		super(registry, deferredRegister);
	}

	public TyrannoBlockRegister(TyrannoRegistry registry) 
	{
		super(registry, DeferredRegister.create(ForgeRegistries.BLOCKS, registry.getModId()));
	}
	
	public <B extends Block> RegistryObject<B> build(String id, B block)
	{
		return this.deferredRegister.register(id, () -> block);
	}
	
	public Pair<RegistryObject<TyrannoStandingSignBlock>, RegistryObject<TyrannoWallSignBlock>> build(String id, AbstractBlock.Properties blockProperties, Properties itemProperties)
	{
		WoodType type = TyrannoSignManager.registerWoodType(WoodType.create(this.registry.getModId() + ":" + id));
		DeferredRegister<Item> itemRegister = registry.getHelper(ForgeRegistries.ITEMS).getDeferredRegister();
		RegistryObject<TyrannoStandingSignBlock> standing = this.deferredRegister.register(id + "_sign", () -> new TyrannoStandingSignBlock(blockProperties, type));
		RegistryObject<TyrannoWallSignBlock> wall = this.deferredRegister.register(id, () -> new TyrannoWallSignBlock(blockProperties.dropsLike(standing.get()), type));
		itemRegister.register(id + "_sign", () -> new SignItem(itemProperties, standing.get(), wall.get()));
		return Pair.of(standing, wall);
	}
}
