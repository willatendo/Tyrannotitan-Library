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

public class TyrannoTripleBushBlock extends TyrannoBushBlock
{
	public static final IntegerProperty LAYER = IntegerProperty.create("layer", 0, 2);

	public TyrannoTripleBushBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) 
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
	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		BlockPos blockpos = context.getClickedPos();
		return blockpos.getY() < context.getLevel().dimensionType().logicalHeight() - 1 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) && context.getLevel().getBlockState(blockpos.above(2)).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
	}

	public void placeAt(IWorld world, BlockPos pos, int flags) 
	{
		world.setBlock(pos, this.defaultBlockState().setValue(LAYER, 0), flags);
		world.setBlock(pos.above(), this.defaultBlockState().setValue(LAYER, 1), flags);
		world.setBlock(pos.above(2), this.defaultBlockState().setValue(LAYER, 2), flags);
	}

	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) 
	{
		world.setBlock(pos, this.defaultBlockState().setValue(LAYER, 0), 2);
		world.setBlock(pos.above(), this.defaultBlockState().setValue(LAYER, 1), 2);
		world.setBlock(pos.above(2), this.defaultBlockState().setValue(LAYER, 2), 2);
	}

	@Override
	public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) 
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
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LAYER);
	}
}
