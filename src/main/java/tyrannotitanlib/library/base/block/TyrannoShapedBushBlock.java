package tyrannotitanlib.library.base.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TyrannoShapedBushBlock extends TyrannoBushBlock {
	private double minX;
	private double minY;
	private double minZ;
	private double maxX;
	private double maxY;
	private double maxZ;

	private final VoxelShape SHAPE = box(minX, minY, minZ, maxX, maxY, maxZ);

	public TyrannoShapedBushBlock(Properties properties, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		super(properties);
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
}
