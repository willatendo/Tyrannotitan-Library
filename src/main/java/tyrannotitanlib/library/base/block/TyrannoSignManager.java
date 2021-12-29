package tyrannotitanlib.library.base.block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;

public class TyrannoSignManager {
	public static final List<Supplier<? extends Block>> SIGN_BLOCKS = new ArrayList<>();

	public static void registerSignBlock(Supplier<? extends Block> sign) {
		synchronized (SIGN_BLOCKS) {
			SIGN_BLOCKS.add(sign);
		}
	}

	public static void forEachSignBlock(Consumer<? super Block> consumer) {
		SIGN_BLOCKS.forEach(block -> consumer.accept(block.get()));
	}
}
