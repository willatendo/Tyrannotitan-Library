package tyrannotitanlib.library.base.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class TyrannoShapedBushBlock extends TyrannoBushBlock
{
	private double minX;
	private double minY;
	private double minZ;
	private double maxX;
	private double maxY;
	private double maxZ;
	
	private final VoxelShape SHAPE = box(minX, minY, minZ, maxX, maxY, maxZ);
	
	public TyrannoShapedBushBlock(Properties properties, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) 
	{
		super(properties);
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
}
