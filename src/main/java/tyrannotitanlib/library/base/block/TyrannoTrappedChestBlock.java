package tyrannotitanlib.library.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import tyrannotitanlib.library.base.tileentity.TyrannoTrappedChestTileEntity;

public class TyrannoTrappedChestBlock extends ChestBlock implements ITyrannoChestBlock 
{
	public final String type;

	public TyrannoTrappedChestBlock(String type, Properties properties) 
	{
		super(properties, null);
		this.type = type;
	}
	
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) 
	{
		return new TyrannoTrappedChestTileEntity();
	}

	@Override
	public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) 
	{
		return false;
	}

	@Override
	public String getChestType() 
	{
		return this.type;
	}

	@Override
	protected Stat<ResourceLocation> getOpenChestStat() 
	{
		return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
	}

	@Override
	public boolean isSignalSource(BlockState state) 
	{
		return true;
	}

	@Override
	public int getSignal(BlockState state, IBlockReader reader, BlockPos pos, Direction direction) 
	{
		return MathHelper.clamp(ChestTileEntity.getOpenCount(reader, pos), 0, 15);
	}

	@Override
	public int getDirectSignal(BlockState state, IBlockReader reader, BlockPos pos, Direction direction) 
	{
		return direction == Direction.UP ? state.getSignal(reader, pos, direction) : 0;
	}
}
