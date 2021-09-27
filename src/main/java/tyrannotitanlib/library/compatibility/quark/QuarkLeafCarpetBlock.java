package tyrannotitanlib.library.compatibility.quark;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class QuarkLeafCarpetBlock extends Block 
{
	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

	public QuarkLeafCarpetBlock(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) 
	{
		return true;
	}
	
	@Nonnull
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}

	@Nonnull
	@Override
	public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, ISelectionContext context) 
	{
		return VoxelShapes.empty();
	}
}
