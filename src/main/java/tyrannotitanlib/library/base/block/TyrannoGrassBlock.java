package tyrannotitanlib.library.base.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

public class TyrannoGrassBlock extends Block
{
	private static Block dirt;
	
	public TyrannoGrassBlock(final Properties properties, final Block dirtBlock) 
	{
		super(properties.randomTicks());
		dirt = dirtBlock;
	}
	
	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		if(!worldIn.isClientSide()) 
		{
			if(!worldIn.isAreaLoaded(pos, 3)) return;
			if(!isLightEnough(state, worldIn, pos)) 
			{
				worldIn.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
			} 
			else if(worldIn.getMaxLocalRawBrightness(pos.above()) >= 9) 
			{
				BlockState blockstate = this.defaultBlockState();
				
				for(int i = 0; i < 4; ++i) 
				{
					BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
					if(worldIn.getBlockState(blockpos).getBlock() == dirt && isValidBonemealTargetGrass(blockstate, worldIn, blockpos))
					{
						worldIn.setBlockAndUpdate(blockpos, blockstate);
					}
				}
			}
		}
	}
	
	private static boolean isLightEnough(BlockState state, IWorldReader reader, BlockPos pos) 
	{
		BlockPos blockpos = pos.above();
		BlockState blockstate = reader.getBlockState(blockpos);
		
		int i = LightEngine.getLightBlockInto(reader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(reader, blockpos));
		return i < reader.getMaxLightLevel();
	}
	
	private static boolean isValidBonemealTargetGrass(BlockState state, IWorldReader reader, BlockPos pos) 
	{
		BlockPos blockpos = pos.above();
		return isLightEnough(state, reader, pos) && !reader.getFluidState(blockpos).is(FluidTags.WATER);
	}
	
	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) 
	{
		return true;
	}
}
