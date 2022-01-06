package tyrannotitanlib.library.compatibility.quark;

//Quark is for 1.18.1, but I'm not updating this yet

@Deprecated(since = "2.0.0", forRemoval = false)
public class QuarkHedgeBlock {}
//extends FenceBlock 
//{
//	public static final Tag<Block> HEDGES = BlockTags.createOptional(new ResourceLocation("quark", "hedges"));
//	private static final BooleanProperty EXTEND = BooleanProperty.create("extend");
//
//	public QuarkHedgeBlock(Properties properties) 
//	{
//		super(properties);
//		this.registerDefaultState(this.defaultBlockState().setValue(EXTEND, false));
//	}
//
//	@Override
//	public boolean connectsTo(BlockState state, boolean isSideSolid, Direction direction) 
//	{
//		return state.is(HEDGES);
//	}
//
//	@Override
//	public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) 
//	{
//		return facing == Direction.UP && !state.getValue(WATERLOGGED) && plantable.getPlantType(world, pos) == PlantType.PLAINS;
//	}
//
//	@Override
//	public BlockState getStateForPlacement(BlockPlaceContext context) 
//	{
//		return super.getStateForPlacement(context).setValue(EXTEND, context.getLevel().getBlockState(context.getClickedPos().below()).is(HEDGES));
//	}
//
//	@Override
//	public BlockState updateShape(BlockState state, Direction facing, BlockState newState, LevelAccessor world, BlockPos pos, BlockPos newPos) 
//	{
//		if(state.getValue(WATERLOGGED)) 
//		{
//			world.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
//		}
//
//		return facing == Direction.DOWN ? state.setValue(EXTEND, newState.is(HEDGES)) : super.updateShape(state, facing, newState, world, pos, newPos);
//	}
//
//	@Override
//	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) 
//	{
//		super.createBlockStateDefinition(builder);
//		builder.add(EXTEND);
//	}
//}
