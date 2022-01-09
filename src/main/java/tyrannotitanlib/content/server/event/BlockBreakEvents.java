package tyrannotitanlib.content.server.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import tyrannotitanlib.library.base.block.legacy.loot.ILoot;
import tyrannotitanlib.library.base.block.legacy.tool.IToolHarvestable;
import tyrannotitanlib.library.utils.TyrannoUtils;

@Deprecated //Comming soon
@EventBusSubscriber(bus = Bus.FORGE, modid = TyrannoUtils.TYRANNO_ID)
public class BlockBreakEvents {
	@SubscribeEvent
	public static void doLoot(BreakEvent event) {
		BlockPos pos = event.getPos();
		LevelAccessor levelaccessor = event.getWorld();
		BlockState blockstate = levelaccessor.getBlockState(pos);
		Block block = blockstate.getBlock();
		if (block instanceof ILoot loot) {
			if (levelaccessor instanceof Level world) {
				if (blockstate.requiresCorrectToolForDrops()) {
					Player player = event.getPlayer();
					Item held = player.getMainHandItem().getItem();
					if (held instanceof DiggerItem digger) {
						if (digger.isCorrectToolForDrops(digger.getDefaultInstance(), blockstate)) {
							dropLoot(world, blockstate, pos);
						}
					}
				} else {
					dropLoot(world, blockstate, pos);
				}
			}
		}
	}

	private static void dropLoot(Level world, BlockState blockstate, BlockPos pos) {
		Block block = blockstate.getBlock();
		if (block instanceof ILoot loot) {
			for (int i = 0; i < loot.amount(blockstate); i++) {
				Block.popResource(world, pos, loot.drop(blockstate).getDefaultInstance());
			}
		}
	}

	@SubscribeEvent
	public static void doHarvestLogic(BreakSpeed event) {
		BlockState blockstate = event.getState();
		Block block = blockstate.getBlock();
		Item held = event.getPlayer().getMainHandItem().getItem();
		if (block instanceof IToolHarvestable harvestable) {
			if (held instanceof DiggerItem digger) {
				if (TierSortingRegistry.isCorrectTierForDrops(digger.getTier(), blockstate)) {
					harvestSpeed(event, digger, blockstate);
				}
			}
		}
	}

	private static void harvestSpeed(BreakSpeed event, Item item, BlockState state) {
		event.setNewSpeed(item.getDestroySpeed(item.getDefaultInstance(), state));
	}
}
