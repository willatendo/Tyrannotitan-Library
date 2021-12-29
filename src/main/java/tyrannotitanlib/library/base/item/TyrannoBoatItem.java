package tyrannotitanlib.library.base.item;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import tyrannotitanlib.library.base.entity.TyrannoBoatEntity;

public class TyrannoBoatItem extends Item {
	private static final Predicate<Entity> COLLISION_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
	private final String type;

	public TyrannoBoatItem(String type, Item.Properties properties) {
		super(properties);
		this.type = type;
		DispenserBlock.registerBehavior(this, new DispenserBoatBehavior(type));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
		} else {
			Vec3 vec3d = player.getViewVector(1.0F);
			List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3d.scale(5.0D)).inflate(1.0D), COLLISION_PREDICATE);
			if (!list.isEmpty()) {
				Vec3 vec3d1 = player.getEyePosition(1.0F);

				for (Entity entity : list) {
					AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
					if (aabb.contains(vec3d1)) {
						return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
					}
				}
			}

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				TyrannoBoatEntity boat = new TyrannoBoatEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
				boat.setBoat(this.type);
				boat.setYRot(player.getYRot());
				if (!level.noCollision(boat, boat.getBoundingBox().inflate(-0.1D))) {
					return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
				} else {
					if (!level.isClientSide) {
						level.addFreshEntity(boat);
					}

					if (!player.getAbilities().instabuild) {
						itemstack.shrink(1);
					}

					player.awardStat(Stats.ITEM_USED.get(this));
					return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
				}
			} else {
				return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
			}
		}
	}

	static class DispenserBoatBehavior extends DefaultDispenseItemBehavior {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
		private final String type;

		public DispenserBoatBehavior(String type) {
			this.type = type;
		}

		public ItemStack execute(BlockSource iBlockSource, ItemStack stack) {
			Direction direction = iBlockSource.getBlockState().getValue(DispenserBlock.FACING);
			Level world = iBlockSource.getLevel();
			double x = iBlockSource.x() + (double) ((float) direction.getStepX() * 1.125f);
			double y = iBlockSource.y() + (double) ((float) direction.getStepY() * 1.125f);
			double z = iBlockSource.z() + (double) ((float) direction.getStepZ() * 1.125f);
			BlockPos pos = iBlockSource.getPos().relative(direction);
			double adjustY;
			if (world.getFluidState(pos).is(FluidTags.WATER)) {
				adjustY = 1d;
			} else {
				if (!world.getBlockState(pos).isAir() || !world.getFluidState(pos.below()).is(FluidTags.WATER)) {
					return this.defaultDispenseItemBehavior.dispense(iBlockSource, stack);
				}
				adjustY = 0d;
			}
			TyrannoBoatEntity boat = new TyrannoBoatEntity(world, x, y + adjustY, z);
			boat.setBoat(this.type);
			boat.setYRot(direction.toYRot());
			world.addFreshEntity(boat);
			stack.shrink(1);
			return stack;
		}

		protected void playSound(BlockSource iBlockSource) {
			iBlockSource.getLevel().levelEvent(1000, iBlockSource.getPos(), 0);
		}
	}
}
