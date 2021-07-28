package tyrannotitanlib.library.base.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class TyrannoQuadruplePlantBlock2 extends TyrannoBushBlock
{
	public static final IntegerProperty LAYER = IntegerProperty.create("layer", 0, 3);

	public TyrannoQuadruplePlantBlock2(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) 
	{
		if(state.getBlock() == this && state.getValue(LAYER) == 0) 
		{
			BlockState soil = worldIn.getBlockState(pos.below());
			return soil.getBlock().canSustainPlant(soil, worldIn, pos.below(), Direction.UP, this);
		} 
		else 
		{
			if (state.getBlock() == this && state.getValue(LAYER) != 0) 
			{
				BlockState below = worldIn.getBlockState(pos.below());
				return below.getBlock() == this;
			}
		}
		BlockState blockstate = worldIn.getBlockState(pos.below());
		if(state.getBlock() != this)
			return super.canSurvive(state, worldIn, pos);
		return blockstate.getBlock() == this && blockstate.getValue(LAYER) == 0;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockPos blockpos = context.getClickedPos();
		return blockpos.getY() < context.getLevel().dimensionType().logicalHeight() - 1 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) && context.getLevel().getBlockState(blockpos.above(2)).canBeReplaced(context) && context.getLevel().getBlockState(blockpos.above(3)).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
	}

	public void placeAt(IWorld worldIn, BlockPos pos, int flags) 
	{
		worldIn.setBlock(pos, this.defaultBlockState().setValue(LAYER, 0), flags);
		worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(LAYER, 1), flags);
		worldIn.setBlock(pos.above(2), this.defaultBlockState().setValue(LAYER, 2), flags);
		worldIn.setBlock(pos.above(3), this.defaultBlockState().setValue(LAYER, 3), flags);
	}

	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) 
	{
		worldIn.setBlock(pos, this.defaultBlockState().setValue(LAYER, 0), 2);
		worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(LAYER, 1), 2);
		worldIn.setBlock(pos.above(2), this.defaultBlockState().setValue(LAYER, 2), 2);
		worldIn.setBlock(pos.above(3), this.defaultBlockState().setValue(LAYER, 3), 2);
	}

	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) 
	{
		if(state.getValue(LAYER) == 0) 
		{
			if(!player.isCreative()) 
			{
				worldIn.destroyBlock(pos, true);
				worldIn.destroyBlock(pos.above(), false);
				worldIn.destroyBlock(pos.above(2), false);
				worldIn.destroyBlock(pos.above(3), false);
				worldIn.destroyBlock(pos.above(4), false);
			} 
			else 
			{
				worldIn.destroyBlock(pos, false);
				worldIn.destroyBlock(pos.above(), false);
				worldIn.destroyBlock(pos.above(2), false);
				worldIn.destroyBlock(pos.above(3), false);
				worldIn.destroyBlock(pos.above(4), false);
			}
		} 
		else if(state.getValue(LAYER) == 1) 
		{
			if(!player.isCreative()) 
			{
				worldIn.destroyBlock(pos.below(), true);
				worldIn.destroyBlock(pos, false);
				worldIn.destroyBlock(pos.above(), false);
				worldIn.destroyBlock(pos.above(2), false);
				worldIn.destroyBlock(pos.above(3), false);
			} 
			else 
			{
				worldIn.destroyBlock(pos.below(), false);
				worldIn.destroyBlock(pos, false);
				worldIn.destroyBlock(pos.above(), false);
				worldIn.destroyBlock(pos.above(2), false);
				worldIn.destroyBlock(pos.above(3), false);
			}
		} 
		else if(state.getValue(LAYER) == 2) 
		{
			if(!player.isCreative()) 
			{
				worldIn.destroyBlock(pos.below(2), true);
				worldIn.destroyBlock(pos.below(), false);
				worldIn.destroyBlock(pos, false);
				worldIn.destroyBlock(pos.above(), false);
				worldIn.destroyBlock(pos.above(2), false);
			} 
			else 
			{
				worldIn.destroyBlock(pos.below(2), false);
				worldIn.destroyBlock(pos.below(), false);
				worldIn.destroyBlock(pos, false);
				worldIn.destroyBlock(pos.above(), false);
				worldIn.destroyBlock(pos.above(2), false);
			}
		} 
		else if(state.getValue(LAYER) == 3) 
		{
			if(!player.isCreative()) 
			{
				worldIn.destroyBlock(pos.below(3), true);
				worldIn.destroyBlock(pos.below(2), false);
				worldIn.destroyBlock(pos.below(), false);
				worldIn.destroyBlock(pos, false);
				worldIn.destroyBlock(pos.above(), false);
			} 
			else 
			{
				worldIn.destroyBlock(pos.below(3), false);
				worldIn.destroyBlock(pos.below(2), false);
				worldIn.destroyBlock(pos.below(), false);
				worldIn.destroyBlock(pos, false);
				worldIn.destroyBlock(pos.above(), false);
			}
		}
		super.playerWillDestroy(worldIn, pos, state, player);
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
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LAYER);
	}
}
