package tyrannotitanlib.library.compatibility.quark;

//Quark is not for 1.17.1

@Deprecated
public class QuarkWoodenPostBlock {}
//extends ChainBlock 
//{
//	private static final VoxelShape SHAPE_X = Block.box(0F, 6F, 6F, 16F, 10F, 10F);
//	private static final VoxelShape SHAPE_Y = Block.box(6F, 0F, 6F, 10F, 16F, 10F);
//	private static final VoxelShape SHAPE_Z = Block.box(6F, 6F, 0F, 10F, 10F, 16F);
//
//	public QuarkWoodenPostBlock(Properties properties) 
//	{
//		super(properties);
//	}
//
//	
//	@Override
//	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) 
//	{
//		switch((Direction.Axis)state.getValue(AXIS)) 
//		{
//			case X:	
//			default:
//				return SHAPE_X;
//			case Z:
//				return SHAPE_Z;
//			case Y:
//				return SHAPE_Y;
//		}
//	}
//}
