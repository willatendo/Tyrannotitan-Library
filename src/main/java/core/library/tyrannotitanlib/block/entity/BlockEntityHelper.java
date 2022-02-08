package core.library.tyrannotitanlib.block.entity;

import static core.content.Util.LOG;

import java.util.Optional;

import javax.annotation.Nullable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockEntityHelper {
	public static <T> Optional<T> get(Class<T> clazz, @Nullable BlockGetter world, BlockPos pos) {
		return get(clazz, world, pos, false);
	}

	public static <T> Optional<T> get(Class<T> clazz, @Nullable BlockGetter world, BlockPos pos, boolean logWrongType) {
		if (!isBlockLoaded(world, pos)) {
			return Optional.empty();
		}

		BlockEntity tile = world.getBlockEntity(pos);
		if (tile == null) {
			return Optional.empty();
		}

		if (clazz.isInstance(tile)) {
			return Optional.of(clazz.cast(tile));
		} else if (logWrongType) {
			LOG.warn("Unexpected TileEntity class at {}, expected {}, but found: {}", pos, clazz, tile.getClass());
		}

		return Optional.empty();
	}

	public static boolean isBlockLoaded(@Nullable BlockGetter world, BlockPos pos) {
		if (world == null) {
			return false;
		}
		if (world instanceof LevelReader) {
			return ((LevelReader) world).hasChunkAt(pos);
		}
		return true;
	}

	@Nullable
	public static <HAVE extends BlockEntity, RET extends BlockEntity> BlockEntityTicker<RET> castTicker(BlockEntityType<RET> expected, BlockEntityType<HAVE> have, BlockEntityTicker<? super HAVE> ticker) {
		return have == expected ? (BlockEntityTicker<RET>) ticker : null;
	}

	@Nullable
	public static <HAVE extends BlockEntity, RET extends BlockEntity> BlockEntityTicker<RET> serverTicker(Level level, BlockEntityType<RET> expected, BlockEntityType<HAVE> have, BlockEntityTicker<? super HAVE> ticker) {
		return level.isClientSide ? null : castTicker(expected, have, ticker);
	}
}
