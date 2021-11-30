package tyrannotitanlib.library.base.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TyrannoQuadrupleBushBlock extends TyrannoBushBlock
{
	public static final IntegerProperty LAYER = IntegerProperty.create("layer", 0, 3);

	public TyrannoQuadrupleBushBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) 
	{
		if(state.getBlock() == this && state.getValue(LAYER) == 0) 
		{
			BlockState soil = world.getBlockState(pos.below());
			return soil.getBlock().canSustainPlant(soil, world, pos.below(), Direction.UP, this);
		} 
		else 
		{
			if (state.getBlock() == this && state.getValue(LAYER) != 0) 
			{
				BlockState below = world.getBlockState(pos.below());
				return below.getBlock() == this;
			}
		}
		BlockState blockstate = world.getBlockState(pos.below());
		if(state.getBlock() != this)
			return super.canSurvive(state, world, pos);
		return blockstate.getBlock() == this && blockstate.getValue(LAYER) == 0;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		BlockPos blockpos = context.getClickedPos();
		return blockpos.getY() < context.getLevel().dimensionType().logicalHeight() - 1 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) && context.getLevel().getBlockState(blockpos.above(2)).canBeReplaced(context) && context.getLevel().getBlockState(blockpos.above(3)).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
	}

	public void placeAt(LevelAccessor world, BlockPos pos, int flags) 
	{
		world.setBlock(pos, this.defaultBlockState().setValue(LAYER, 0), flags);
		world.setBlock(pos.above(), this.defaultBlockState().setValue(LAYER, 1), flags);
		world.setBlock(pos.above(2), this.defaultBlockState().setValue(LAYER, 2), flags);
		world.setBlock(pos.above(3), this.defaultBlockState().setValue(LAYER, 3), flags);
	}

	public void onBlockPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) 
	{
		world.setBlock(pos, this.defaultBlockState().setValue(LAYER, 0), 2);
		world.setBlock(pos.above(), this.defaultBlockState().setValue(LAYER, 1), 2);
		world.setBlock(pos.above(2), this.defaultBlockState().setValue(LAYER, 2), 2);
		world.setBlock(pos.above(3), this.defaultBlockState().setValue(LAYER, 3), 2);
	}

	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) 
	{
		if(state.getValue(LAYER) == 0) 
		{
			if(!player.isCreative()) 
			{
				world.destroyBlock(pos, true);
				world.destroyBlock(pos.above(), false);
				world.destroyBlock(pos.above(2), false);
				world.destroyBlock(pos.above(3), false);
				world.destroyBlock(pos.above(4), false);
			} 
			else 
			{
				world.destroyBlock(pos, false);
				world.destroyBlock(pos.above(), false);
				world.destroyBlock(pos.above(2), false);
				world.destroyBlock(pos.above(3), false);
				world.destroyBlock(pos.above(4), false);
			}
		} 
		else if(state.getValue(LAYER) == 1) 
		{
			if(!player.isCreative()) 
			{
				world.destroyBlock(pos.below(), true);
				world.destroyBlock(pos, false);
				world.destroyBlock(pos.above(), false);
				world.destroyBlock(pos.above(2), false);
				world.destroyBlock(pos.above(3), false);
			} 
			else 
			{
				world.destroyBlock(pos.below(), false);
				world.destroyBlock(pos, false);
				world.destroyBlock(pos.above(), false);
				world.destroyBlock(pos.above(2), false);
				world.destroyBlock(pos.above(3), false);
			}
		} 
		else if(state.getValue(LAYER) == 2) 
		{
			if(!player.isCreative()) 
			{
				world.destroyBlock(pos.below(2), true);
				world.destroyBlock(pos.below(), false);
				world.destroyBlock(pos, false);
				world.destroyBlock(pos.above(), false);
				world.destroyBlock(pos.above(2), false);
			} 
			else 
			{
				world.destroyBlock(pos.below(2), false);
				world.destroyBlock(pos.below(), false);
				world.destroyBlock(pos, false);
				world.destroyBlock(pos.above(), false);
				world.destroyBlock(pos.above(2), false);
			}
		} 
		else if(state.getValue(LAYER) == 3) 
		{
			if(!player.isCreative()) 
			{
				world.destroyBlock(pos.below(3), true);
				world.destroyBlock(pos.below(2), false);
				world.destroyBlock(pos.below(), false);
				world.destroyBlock(pos, false);
				world.destroyBlock(pos.above(), false);
			} 
			else 
			{
				world.destroyBlock(pos.below(3), false);
				world.destroyBlock(pos.below(2), false);
				world.destroyBlock(pos.below(), false);
				world.destroyBlock(pos, false);
				world.destroyBlock(pos.above(), false);
			}
		}
		super.playerWillDestroy(world, pos, state, player);
	}

	public BlockState getStateFromMeta(int meta) 
	{
		return this.defaultBlockState().setValue(LAYER, meta);
	}

	public int getMetaFromState(BlockState state) 
	{
		return state.getValue(LAYER);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LAYER);
	}
}
