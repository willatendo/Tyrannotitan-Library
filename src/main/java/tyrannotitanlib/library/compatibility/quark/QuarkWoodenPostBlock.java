package tyrannotitanlib.library.compatibility.quark;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class QuarkWoodenPostBlock extends ChainBlock 
{
	private static final VoxelShape SHAPE_X = Block.box(0F, 6F, 6F, 16F, 10F, 10F);
	private static final VoxelShape SHAPE_Y = Block.box(6F, 0F, 6F, 10F, 16F, 10F);
	private static final VoxelShape SHAPE_Z = Block.box(6F, 6F, 0F, 10F, 10F, 16F);

	public QuarkWoodenPostBlock(Properties properties) 
	{
		super(properties);
	}

	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) 
	{
		switch((Direction.Axis)state.getValue(AXIS)) 
		{
			case X:	
			default:
				return SHAPE_X;
			case Z:
				return SHAPE_Z;
			case Y:
				return SHAPE_Y;
		}
	}
}
