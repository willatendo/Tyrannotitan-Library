package tyrannotitanlib.tyrannimation.util;

import java.util.Objects;

import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import tyrannotitanlib.tyrannimation.core.controller.AnimationController;
import tyrannotitanlib.tyrannimation.core.manager.AnimatedFactory;
import tyrannotitanlib.tyrannimation.world.storage.TyrannoLibIdTracker;
import tyrannotitanlib.tyrannimation.world.storage.TyrannoLibIdTracker.Type;

public class AnimatedUtilities {
	private static final String TYRANNIMATION_NBT = "tyrannimation";

	public static int getIDFromStack(ItemStack stack) {
		if (stackHasIDTag(stack)) {
			return stack.getTag().getInt(TYRANNIMATION_NBT);
		}
		return Objects.hash(stack.getItem().getRegistryName(), stack.getTag(), stack.getCount());
	}

	public static void writeIDToStack(ItemStack stack, ServerLevel world) {
		if (!stackHasIDTag(stack)) {
			final int id = TyrannoLibIdTracker.from(world).getNextId(Type.ITEM);
			stack.getOrCreateTag().putInt(TYRANNIMATION_NBT, id);
		}
	}

	public static int guaranteeIDForStack(ItemStack stack, ServerLevel world) {
		if (!stackHasIDTag(stack)) {
			final int id = TyrannoLibIdTracker.from(world).getNextId(Type.ITEM);
			stack.getOrCreateTag().putInt(TYRANNIMATION_NBT, id);
			return id;
		} else {
			return stack.getTag().getInt(TYRANNIMATION_NBT);
		}
	}

	public static void removeIDFromStack(ItemStack stack) {
		if (stackHasIDTag(stack)) {
			stack.getTag().remove(TYRANNIMATION_NBT);
		}
	}

	public static boolean stackHasIDTag(ItemStack stack) {
		return stack.hasTag() && stack.getTag().contains(TYRANNIMATION_NBT, Tag.TAG_INT);
	}

	public static AnimationController getControllerForStack(AnimatedFactory factory, ItemStack stack, String controllerName) {
		return getControllerForID(factory, getIDFromStack(stack), controllerName);
	}

	public static AnimationController getControllerForID(AnimatedFactory factory, Integer id, String controllerName) {
		return factory.getOrCreateAnimationData(id).getAnimatedControllers().get(controllerName);
	}
}
