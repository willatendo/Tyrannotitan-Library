package tyrannotitanlib.library.compatibility.quark;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class QuarkVerticalSlabBlock extends Block implements IWaterLoggable 
{
	public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public QuarkVerticalSlabBlock(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, VerticalSlabType.NORTH).setValue(WATERLOGGED, false));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.getValue(TYPE) == VerticalSlabType.DOUBLE ? state : state.setValue(TYPE, VerticalSlabType.fromDirection(rot.rotate(state.getValue(TYPE).direction)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		VerticalSlabType type = state.getValue(TYPE);
		if(type == VerticalSlabType.DOUBLE || mirrorIn == Mirror.NONE)
		{
			return state;
		}
		
		if((mirrorIn == Mirror.LEFT_RIGHT && type.direction.getAxis() == Axis.Z) || (mirrorIn == Mirror.FRONT_BACK && type.direction.getAxis() == Axis.X))
		{
			return state.setValue(TYPE, VerticalSlabType.fromDirection(state.getValue(TYPE).direction.getOpposite()));
		}
		
		return state;
	}
	
	@Override
	public boolean isAir(BlockState state) 
	{
		return state.getValue(TYPE) != VerticalSlabType.DOUBLE;
	}
	
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) 
	{
		builder.add(TYPE, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) 
	{
		return state.getValue(TYPE).shape;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = context.getLevel().getBlockState(blockpos);
		if(blockstate.getBlock() == this) 
		{
			return blockstate.setValue(TYPE, VerticalSlabType.DOUBLE).setValue(WATERLOGGED, false);
		}
		
		FluidState fluid = context.getLevel().getFluidState(blockpos);
		BlockState retState = defaultBlockState().setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
		Direction direction = getDirectionForPlacement(context);
		VerticalSlabType type = VerticalSlabType.fromDirection(direction);
		
		return retState.setValue(TYPE, type);
	}

	private Direction getDirectionForPlacement(BlockItemUseContext context) 
	{
		Direction direction = context.getClickedFace();
		if(direction.getAxis() != Axis.Y)
		{
			return direction;
		}
		
		BlockPos pos = context.getClickedPos();
		Vector3d vec = context.getClickLocation().subtract(new Vector3d(pos.getX(), pos.getY(), pos.getZ())).subtract(0.5, 0, 0.5);
		double angle = Math.atan2(vec.x, vec.z) * -180.0 / Math.PI;
		return Direction.fromYRot(angle).getOpposite();
	}

	@Override
	public boolean canBeReplaced(BlockState state, @Nonnull BlockItemUseContext context) 
	{
		ItemStack itemstack = context.getItemInHand();
		VerticalSlabType slabtype = state.getValue(TYPE);
		return slabtype != VerticalSlabType.DOUBLE && itemstack.getItem() == this.asItem() && (context.replacingClickedOnBlock() && (context.getClickedFace() == slabtype.direction && getDirectionForPlacement(context) == slabtype.direction) || (!context.replacingClickedOnBlock() && context.getClickedFace().getAxis() != slabtype.direction.getAxis()));
	}

	@Override
	public FluidState getFluidState(BlockState state) 
	{
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
	}

	@Override
	public boolean placeLiquid(IWorld world, BlockPos pos, BlockState state, FluidState fluidStateIn) 
	{
		return state.getValue(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.placeLiquid(world, pos, state, fluidStateIn);
	}

	@Override
	public boolean canPlaceLiquid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluidIn) 
	{
		return state.getValue(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.canPlaceLiquid(world, pos, state, fluidIn);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) 
	{
		if(state.getValue(WATERLOGGED)) 
		{
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		return state;
	}
	
	@Override
	public boolean isPathfindable(BlockState state, IBlockReader world, BlockPos pos, PathType type) 
	{
		return type == PathType.WATER && world.getFluidState(pos).is(FluidTags.WATER);
	}

	public static enum VerticalSlabType implements IStringSerializable 
	{
		NORTH(Direction.NORTH),
		SOUTH(Direction.SOUTH),
		WEST(Direction.WEST),
		EAST(Direction.EAST),
		DOUBLE(null);

		private final String name;
		@Nullable
		public final Direction direction;
		public final VoxelShape shape;

		VerticalSlabType(@Nullable Direction direction) 
		{
			this.direction = direction;
			this.name = direction == null ? "double" : direction.getSerializedName();
			if(direction == null) 
			{
				this.shape = VoxelShapes.block();
			} 
			else 
			{
				boolean isNegativeAxis = direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE;
				double min = isNegativeAxis ? 8 : 0;
				double max = isNegativeAxis ? 16 : 8;
				this.shape = direction.getAxis() == Direction.Axis.X ? Block.box(min, 0, 0, max, 16, 16) : Block.box(0, 0, min, 16, 16, max);
			}
		}

		public static VerticalSlabType fromDirection(Direction direction) 
		{
			for(VerticalSlabType type : VerticalSlabType.values()) 
			{
				if(type.direction != null && direction == type.direction) 
				{
					return type;
				}
			}
			return null;
		}

		@Override
		public String toString() 
		{
			return this.name;
		}

		@Override
		public String getSerializedName() 
		{
			return this.name;
		}
	}
}