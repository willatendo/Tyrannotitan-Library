package tyrannotitanlib.library.tyrannobook.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import tyrannotitanlib.library.tyrannonetwork.util.TileEntityHelper;

public abstract class TyrannobookItem extends Item implements ITyrannoLecternBookItem {
	public TyrannobookItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = world.getBlockState(pos);
		Player entity = context.getPlayer();
		if (state.is(Blocks.LECTERN)) {
			if (LecternBlock.tryPlaceBook(entity, world, pos, state, context.getItemInHand())) {
				return InteractionResult.sidedSuccess(world.isClientSide);
			}
		}
		return InteractionResult.PASS;
	}

	public static void interactWithBlock(PlayerInteractEvent.RightClickBlock event) {
		Level world = event.getWorld();
		if (world.isClientSide() || event.getPlayer().isCrouching()) {
			return;
		}

		BlockPos pos = event.getPos();
		BlockState state = world.getBlockState(pos);
		if (state.is(Blocks.LECTERN)) {
			TileEntityHelper.getTile(LecternBlockEntity.class, world, pos).ifPresent(te -> {
				ItemStack book = te.getBook();
				if (!book.isEmpty() && book.getItem() instanceof ITyrannoLecternBookItem && ((ITyrannoLecternBookItem) book.getItem()).openLecternScreen(world, pos, event.getPlayer(), book)) {
					event.setCanceled(true);
				}
			});
		}
	}
}
