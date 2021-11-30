package tyrannotitanlib.library.compatibility.decorativeblocks;

//Decorative Blocks is not for 1.17.1

@Deprecated
public class DecorativeBlocksDummyEntityForSitting {}
//extends Entity 
//{
//    public DecorativeBlocksDummyEntityForSitting(Level world)
//    {
//        super(TyrannoEntities.SITTING, world);
//    }
//
//    public DecorativeBlocksDummyEntityForSitting(EntityType<? extends DecorativeBlocksDummyEntityForSitting> type, Level world)
//    {
//        super(type, world);
//    }
//
//    public DecorativeBlocksDummyEntityForSitting(Level world, BlockPos pos)
//    {
//        super(TyrannoEntities.SITTING, world);
//        setPos(pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D);
//        noPhysics = true;
//    }
//
//	@Override
//	protected void defineSynchedData() { }
//
//	@Override
//	protected void readAdditionalSaveData(CompoundTag nbt) { }
//
//	@Override
//	protected void addAdditionalSaveData(CompoundTag nbt) { }
//
//	@Override
//	public Packet<?> getAddEntityPacket() 
//	{
//        return NetworkHooks.getEntitySpawningPacket(this);
//	}
//}
