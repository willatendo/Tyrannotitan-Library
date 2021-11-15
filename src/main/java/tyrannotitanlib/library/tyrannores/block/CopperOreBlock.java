package tyrannotitanlib.library.tyrannores.block;

import java.util.LinkedList;
import java.util.OptionalDouble;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CopperOreBlock extends OreBlock 
{
	public static final IntegerProperty OXIDIZATION = IntegerProperty.create("oxidization", 0, 3);
	private float chance;

	public CopperOreBlock(Properties properties, float chance) 
	{
		super(properties);
		this.chance = chance;
		registerDefaultState(defaultBlockState().setValue(OXIDIZATION, 0));
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) 
	{
		if(world.getRandom().nextFloat() <= chance) 
		{
			int currentState = state.getValue(OXIDIZATION);
			boolean canIncrease = false;
			LinkedList<Integer> neighbours = new LinkedList<>();
			for(Direction facing : Direction.values()) 
			{
				BlockPos neighbourPos = pos.relative(facing);
				if(!world.isAreaLoaded(neighbourPos, 0))
				{
					continue;
				}
				if(!world.isLoaded(neighbourPos))
				{
					continue;
				}
				BlockState neighbourState = world.getBlockState(neighbourPos);
				if(neighbourState.hasProperty(OXIDIZATION) && neighbourState.getValue(OXIDIZATION) != 0) 
				{
					neighbours.add(neighbourState.getValue(OXIDIZATION));
				}
				if(neighbourState.isFaceSturdy(world, pos, facing)) 
				{
					continue;
				}
				canIncrease = true;
			}
			if(canIncrease) 
			{
				OptionalDouble average = neighbours.stream().mapToInt(v -> v).average();
				if(average.orElse(7d) >= currentState)
				{
					world.setBlockAndUpdate(pos, state.setValue(OXIDIZATION, Math.min(currentState + 1, 3)));
				}
			}
		}
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) 
	{
		if(state.getValue(OXIDIZATION) > 0 && player.getItemInHand(hand).getItem() instanceof AxeItem) 
		{
			if(!player.isCreative())
			{
				player.getItemInHand(hand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
			}
			world.setBlockAndUpdate(pos, state.setValue(OXIDIZATION, 0));
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.PASS;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(OXIDIZATION);
		super.createBlockStateDefinition(builder);
	}
}
