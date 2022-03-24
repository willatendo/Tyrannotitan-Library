package tyrannotitanlib.core.content;

import static tyrannotitanlib.core.content.ModUtilities.LOG;
import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_ID;
import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = ModUtilities.TYRANNO_ID, bus = Bus.MOD)
public class CopperFix {
	private static final Map<ResourceLocation, Block> blockRemappings = new HashMap<ResourceLocation, Block>() {
		private static final long serialVersionUID = 2729764913431843323L;
		{
			put(TYRANNO_UTILS.mod("copper_ore"), Blocks.COPPER_ORE);
		}
	};

	private static final Map<ResourceLocation, Item> itemRemappings = new HashMap<ResourceLocation, Item>() {
		private static final long serialVersionUID = 2729764913431843323L;
		{
			put(TYRANNO_UTILS.mod("copper_ingot"), Items.COPPER_INGOT);
		}
	};

	@SubscribeEvent
	public void updateBlockMappings(RegistryEvent.MissingMappings<Block> event) {
		if (event.getAllMappings().stream().filter(mapping -> mapping.key.getNamespace().equals(TYRANNO_ID)).findAny().isPresent()) {
			event.getAllMappings().stream().filter(m -> m.key.getNamespace().equals(TYRANNO_ID)).forEach(mapping -> {
				if (blockRemappings.containsKey(mapping.key)) {
					remap(mapping, blockRemappings);
				}
			});
		}
	}

	@SubscribeEvent
	public void updateItemMappings(RegistryEvent.MissingMappings<Item> event) {
		if (event.getAllMappings().stream().filter(mapping -> mapping.key.getNamespace().equals(TYRANNO_ID)).findAny().isPresent()) {
			event.getAllMappings().stream().filter(mapping -> mapping.key.getNamespace().equals(TYRANNO_ID)).forEach(mapping -> {
				if (itemRemappings.containsKey(mapping.key)) {
					remap(mapping, itemRemappings);
				}
			});
		}
	}

	private <T extends IForgeRegistryEntry<T>> void remap(RegistryEvent.MissingMappings.Mapping<T> mapping, Map<ResourceLocation, T> remappings) {
		ResourceLocation key = mapping.key;
		if (remappings.containsKey(key)) {
			mapping.remap(remappings.get(key));
			LOG.warn("Replaced " + key + " with " + remappings.get(key).getRegistryName());
		} else {
			mapping.ignore();
			LOG.warn("Could not find a mapping replacement for " + key + ". It was likely intentionally removed in an update.");
		}
	}
}
