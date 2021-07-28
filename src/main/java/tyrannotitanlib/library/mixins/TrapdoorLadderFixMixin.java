package tyrannotitanlib.library.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Mixin(TrapDoorBlock.class)
public final class TrapdoorLadderFixMixin 
{
	@Inject(at = @At("RETURN"), method = "isLadder", cancellable = true, remap = false)
	private void isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity, CallbackInfoReturnable<Boolean> info)
	{
		if(state.getValue(TrapDoorBlock.OPEN)) 
		{
			BlockState down = world.getBlockState(pos.below());
			if(down.getBlock() instanceof LadderBlock)
				info.setReturnValue(down.getValue(LadderBlock.FACING) == state.getValue(TrapDoorBlock.FACING));
		}
	}
}
