package tyrannotitanlib.library.compatibility.quark;

//Quark is not for 1.17.1

@Deprecated(since = "2.0.0", forRemoval = false)
public class QuarkLeafCarpetBlock {}
//extends Block 
//{
//	private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);
//
//	public QuarkLeafCarpetBlock(Properties properties) 
//	{
//		super(properties);
//	}
//	
//	@Override
//	public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) 
//	{
//		return true;
//	}
//	
//	@Nonnull
//	@Override
//	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) 
//	{
//		return SHAPE;
//	}
//
//	@Nonnull
//	@Override
//	public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, CollisionContext context) 
//	{
//		return Shapes.empty();
//	}
//}
