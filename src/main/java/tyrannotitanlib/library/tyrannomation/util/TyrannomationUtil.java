package tyrannotitanlib.library.tyrannomation.util;

import java.util.Objects;

import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import tyrannotitanlib.library.tyrannomation.core.controller.TyrannomationController;
import tyrannotitanlib.library.tyrannomation.core.manager.TyrannomationFactory;
import tyrannotitanlib.library.tyrannomation.world.storage.TyrannoLibIdTracker;
import tyrannotitanlib.library.tyrannomation.world.storage.TyrannoLibIdTracker.Type;

public class TyrannomationUtil {
	private static final String TYRANNOMATION_NBT = "TyrannomationId";

	public static int getIDFromStack(ItemStack stack) {
		if (stackHasIDTag(stack)) {
			return stack.getTag().getInt(TYRANNOMATION_NBT);
		}
		return Objects.hash(stack.getItem().getRegistryName(), stack.getTag(), stack.getCount());
	}

	public static void writeIDToStack(ItemStack stack, ServerLevel world) {
		if (!stackHasIDTag(stack)) {
			final int id = TyrannoLibIdTracker.from(world).getNextId(Type.ITEM);
			stack.getOrCreateTag().putInt(TYRANNOMATION_NBT, id);
		}
	}

	public static int guaranteeIDForStack(ItemStack stack, ServerLevel world) {
		if (!stackHasIDTag(stack)) {
			final int id = TyrannoLibIdTracker.from(world).getNextId(Type.ITEM);
			stack.getOrCreateTag().putInt(TYRANNOMATION_NBT, id);
			return id;
		} else {
			return stack.getTag().getInt(TYRANNOMATION_NBT);
		}
	}

	public static void removeIDFromStack(ItemStack stack) {
		if (stackHasIDTag(stack)) {
			stack.getTag().remove(TYRANNOMATION_NBT);
		}
	}

	public static boolean stackHasIDTag(ItemStack stack) {
		return stack.hasTag() && stack.getTag().contains(TYRANNOMATION_NBT, Tag.TAG_INT);
	}

	public static TyrannomationController getControllerForStack(TyrannomationFactory factory, ItemStack stack,
			String controllerName) {
		return getControllerForID(factory, getIDFromStack(stack), controllerName);
	}

	public static TyrannomationController getControllerForID(TyrannomationFactory factory, Integer id,
			String controllerName) {
		return factory.getOrCreateAnimationData(id).getAnimationControllers().get(controllerName);
	}
}
