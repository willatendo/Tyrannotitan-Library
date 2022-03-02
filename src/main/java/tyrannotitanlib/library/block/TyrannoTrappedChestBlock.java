package tyrannotitanlib.library.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tyrannotitanlib.library.block.entity.TyrannoTrappedChestBlockEntity;

public class TyrannoTrappedChestBlock extends ChestBlock implements TyrannoChest {
	public final String type;

	public TyrannoTrappedChestBlock(String type, Properties properties) {
		super(properties, null);
		this.type = type;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TyrannoTrappedChestBlockEntity(pos, state);
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
		return false;
	}

	@Override
	public String getChestType() {
		return this.type;
	}

	@Override
	protected Stat<ResourceLocation> getOpenChestStat() {
		return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	public int getSignal(BlockState state, BlockGetter reader, BlockPos pos, Direction direction) {
		return Mth.clamp(ChestBlockEntity.getOpenCount(reader, pos), 0, 15);
	}

	@Override
	public int getDirectSignal(BlockState state, BlockGetter reader, BlockPos pos, Direction direction) {
		return direction == Direction.UP ? state.getSignal(reader, pos, direction) : 0;
	}
}
