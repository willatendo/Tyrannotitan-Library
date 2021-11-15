package tyrannotitanlib.content;

import static tyrannotitanlib.library.utils.TyrannoUtils.TYRANNO_ID;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Sets;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tyrannotitanlib.content.client.Capes;
import tyrannotitanlib.content.config.TyrannotitanConfig;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.content.server.init.TyrannoRegistries;
import tyrannotitanlib.library.base.block.TyrannoBeehiveBlock;
import tyrannotitanlib.library.base.block.TyrannoStandingSignBlock;
import tyrannotitanlib.library.base.block.TyrannoWallSignBlock;
import tyrannotitanlib.library.base.block.TyrannoWoodType;
import tyrannotitanlib.library.network.Tyrannonetwork;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookLoader;
import tyrannotitanlib.library.tyrannobook.item.TyrannobookItem;
import tyrannotitanlib.library.tyrannoregister.TyrannoRegister;
import tyrannotitanlib.library.tyrannores.world.OreGeneration;
import tyrannotitanlib.library.utils.TyrannoUtils;

@Mod(TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public static final Block SIGN = new TyrannoStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).noCollission().strength(1.0F).sound(SoundType.WOOD), TyrannoWoodType.CUSTOM);
	public static final Block WALL_SIGN = new TyrannoWallSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(SIGN), TyrannoWoodType.CUSTOM);
	public static final Item SIGN_ITEM = new SignItem(new Properties().tab(ItemGroup.TAB_MISC), TyrannotitanLibrary.SIGN, TyrannotitanLibrary.WALL_SIGN);
	
	public TyrannotitanLibrary() 
	{
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		final IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		
		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		
		TyrannoRegistries.register();
				
		TyrannoRegister.registerBlock("sign", SIGN);
		TyrannoRegister.registerBlock("wall_sign", WALL_SIGN);
		TyrannoRegister.registerItem("sign", SIGN_ITEM);		
		
		forgeBus.addListener(EventPriority.HIGH, OreGeneration::addOresToOverworld);
		
		forgeBus.register(new Capes());
		
		ModLoadingContext.get().registerConfig(Type.SERVER, TyrannotitanConfig.serverConfig);
		
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, PlayerInteractEvent.RightClickBlock.class, TyrannobookItem::interactWithBlock);
	}
	
	private void commonSetup(final FMLCommonSetupEvent event) 
	{
		BufferedReader urlContents = TyrannoUtils.getURLContents("https://raw.githubusercontent.com/Willatendo/Tyrannotitan-Library/master/src/main/resources/assets/tyrannotitanlib/tyrannotitan.txt", "assets/tyrannotitanlib/tyrannotitan.txt");
		if(urlContents != null) 
		{
			try 
			{
				String line;
				while((line = urlContents.readLine()) != null) 
				{
					TyrannoUtils.TYRANNOTITANS.add(line);
				}
			} 
			catch(IOException e) 
			{
				TyrannoUtils.LOGGER.warn("Failed to load tyrannotitan member's capes");
			}
		} 
		else
		{
			TyrannoUtils.LOGGER.warn("Failed to load tyrannotitan member's capes");
		}

		Tyrannonetwork.registerPackets();
		
		event.enqueueWork(() -> 
		{
			this.addBeehivePOI();	
		});
	}
	
	private void clientSetup(FMLClientSetupEvent event)
	{
		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if(manager instanceof IReloadableResourceManager) 
		{
			((IReloadableResourceManager)manager).registerReloadListener(new TyrannobookLoader());
		}
	}
	
	private void addBeehivePOI() 
	{
		PointOfInterestType.BEEHIVE.matchingStates = Sets.newHashSet(PointOfInterestType.BEEHIVE.matchingStates);
		Map<BlockState, PointOfInterestType> statePointOfInterestMap = ObfuscationReflectionHelper.getPrivateValue(PointOfInterestType.class, null, "field_221073_u");
		if(statePointOfInterestMap != null) 
		{
			for(Block block : TyrannoBlockEntities.collectBlocks(TyrannoBeehiveBlock.class)) 
			{
				block.getStateDefinition().getPossibleStates().forEach(state -> 
				{
					statePointOfInterestMap.put(state, PointOfInterestType.BEEHIVE);
					PointOfInterestType.BEEHIVE.matchingStates.add(state);
				});
			}
		}
	}
}
