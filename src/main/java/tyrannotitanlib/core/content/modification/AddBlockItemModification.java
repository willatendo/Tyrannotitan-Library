package tyrannotitanlib.core.content.modification;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public record AddBlockItemModification(Item toGivePlace, Block toPlace) {
	public void addNautilusShell(RightClickBlock event) {
		Player player = event.getPlayer();
		InteractionHand hand = event.getHand();
		ItemStack stack = player.getItemInHand(hand);
		Item item = stack.getItem();

		if (item == toGivePlace) {
			Level level = event.getWorld();
			BlockPos pos = event.getPos().relative(event.getFace());
			BlockPos clickedPos = event.getPos();
			Direction direction = player.getDirection().getOpposite();

			if (!(level.getBlockState(clickedPos).getBlock() instanceof EntityBlock)) {
				if (level.getBlockState(pos.below()).isFaceSturdy(level, pos, direction)) {
					player.swing(hand);
					if (!player.isCreative()) {
						stack.shrink(1);
					}
					level.setBlockAndUpdate(pos, toPlace.defaultBlockState());
					level.playSound(player, pos, toPlace.getSoundType(toPlace.defaultBlockState(), level, clickedPos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				}
			}

			if (player.isCrouching()) {
				if (level.getBlockState(pos.below()).isFaceSturdy(level, pos, direction)) {
					player.swing(hand);
					if (!player.isCreative()) {
						stack.shrink(1);
					}
					level.setBlockAndUpdate(pos, toPlace.defaultBlockState());
					level.playSound(player, pos, toPlace.getSoundType(toPlace.defaultBlockState(), level, clickedPos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				}
				event.setCanceled(true);
			}
		}
	}
}
