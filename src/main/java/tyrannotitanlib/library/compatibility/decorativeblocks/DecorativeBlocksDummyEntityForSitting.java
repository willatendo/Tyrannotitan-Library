package tyrannotitanlib.library.compatibility.decorativeblocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import tyrannotitanlib.content.server.init.TyrannoEntities;

public class DecorativeBlocksDummyEntityForSitting extends Entity 
{
    public DecorativeBlocksDummyEntityForSitting(World world)
    {
        super(TyrannoEntities.SITTING, world);
    }

    public DecorativeBlocksDummyEntityForSitting(EntityType<? extends DecorativeBlocksDummyEntityForSitting> type, World world)
    {
        super(type, world);
    }

    public DecorativeBlocksDummyEntityForSitting(World world, BlockPos pos)
    {
        super(TyrannoEntities.SITTING, world);
        setPos(pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
        noPhysics = true;
    }

	@Override
	protected void defineSynchedData() { }

	@Override
	protected void readAdditionalSaveData(CompoundNBT nbt) { }

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) { }

	@Override
	public IPacket<?> getAddEntityPacket() 
	{
        return NetworkHooks.getEntitySpawningPacket(this);
	}
}
