package tyrannotitanlib.content;

import static tyrannotitanlib.library.utils.TyrannoUtils.TYRANNO_ID;

import java.util.Map;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tyrannotitanlib.content.server.init.TyrannoBlockEntities;
import tyrannotitanlib.content.server.init.TyrannoRegistries;
import tyrannotitanlib.library.base.block.TyrannoBeehiveBlock;
import tyrannotitanlib.library.network.Tyrannonetwork;
import tyrannotitanlib.library.tyrannobook.client.TyrannobookLoader;
import tyrannotitanlib.library.tyrannobook.item.TyrannobookItem;

@Mod(TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public TyrannotitanLibrary() 
	{
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::commonSetup);
		
		TyrannoRegistries.register();
		
		MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, PlayerInteractEvent.RightClickBlock.class, TyrannobookItem::interactWithBlock);
	}
	
	private void commonSetup(final FMLCommonSetupEvent event) 
	{
		Tyrannonetwork.registerPackets();
		
		event.enqueueWork(() -> {
			this.addBeehivePOI();	
		});
		
		IResourceManager manager = Minecraft.getInstance().getResourceManager();
		if(manager instanceof IReloadableResourceManager) 
		{
			((IReloadableResourceManager)manager).registerReloadListener(new TyrannobookLoader());
		}
	}
	
	private void addBeehivePOI() 
	{
		PointOfInterestType.BEEHIVE.matchingStates = Sets.newHashSet(PointOfInterestType.BEEHIVE.matchingStates);
		Map<BlockState, PointOfInterestType> statePointOfInterestMap = ObfuscationReflectionHelper.getPrivateValue(PointOfInterestType.class, null, "f_27323_");
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
