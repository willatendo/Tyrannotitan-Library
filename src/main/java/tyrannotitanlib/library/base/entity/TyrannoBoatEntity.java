package tyrannotitanlib.library.base.entity;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import tyrannotitanlib.content.server.init.TyrannoEntities;
import tyrannotitanlib.library.utils.TyrannoBoatRegistry;

public class TyrannoBoatEntity extends Boat 
{
	private static final EntityDataAccessor<String> BOAT_TYPE = SynchedEntityData.defineId(TyrannoBoatEntity.class, EntityDataSerializers.STRING);

	public TyrannoBoatEntity(EntityType<? extends TyrannoBoatEntity> type, Level world) 
	{
		super(type, world);
		this.blocksBuilding = true;
	}

	public TyrannoBoatEntity(Level worldIn, double x, double y, double z) 
	{
		this(TyrannoEntities.BOAT, worldIn);
		this.setPos(x, y, z);
		this.setDeltaMovement(Vec3.ZERO);
		this.xo = x;
		this.yo = y;
		this.zo = z;
	}

	public TyrannoBoatEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level world) 
	{
		this(TyrannoEntities.BOAT, world);
	}

	@Override
	protected void defineSynchedData() 
	{
		super.defineSynchedData();
		this.entityData.define(BOAT_TYPE, "minecraft:oak");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) 
	{
		compound.putString("Type", TyrannoBoatRegistry.getNameForData(this.getBoat()));
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) 
	{
		if(compound.contains("Type", Constants.NBT.TAG_STRING)) 
		{
			String type = compound.getString("Type");
			TyrannoBoatRegistry.BoatData data = TyrannoBoatRegistry.getDataForBoat(type);
			if(data != null)
				this.setBoat(TyrannoBoatRegistry.getNameForData(data));
			else
				this.setBoat(TyrannoBoatRegistry.getBaseBoatName());
		} 
		else 
		{
			this.setBoat(TyrannoBoatRegistry.getBaseBoatName());
		}
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) 
	{
		this.lastYd = this.getDeltaMovement().y;
		if(!this.isPassenger()) 
		{
			if(onGround) 
			{
				if(this.fallDistance > 3.0F) 
				{
					if(this.status != TyrannoBoatEntity.Status.ON_LAND) 
					{
						this.fallDistance = 0.0F;
						return;
					}

					this.causeFallDamage(this.fallDistance, 1.0F);
					if(!this.level.isClientSide && this.isAlive()) 
					{
						this.remove();
						if(this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) 
						{
							for(int i = 0; i < 3; ++i) 
							{
								this.spawnAtLocation(this.getBoat().getPlankItem());
							}

							for(int j = 0; j < 2; ++j) 
							{
								this.spawnAtLocation(Items.STICK);
							}
						}
					}
				}

				this.fallDistance = 0.0F;
			} 
			else if(!this.level.getFluidState((new BlockPos(this.position())).below()).is(FluidTags.WATER) && y < 0.0D) 
			{
				this.fallDistance = (float) ((double) this.fallDistance - y);
			}
		}
	}

	@Override
	public Item getDropItem() 
	{
		return this.getBoat().getBoatItem();
	}

	public void setBoat(String boat) 
	{
		this.entityData.set(BOAT_TYPE, boat);
	}

	public TyrannoBoatRegistry.BoatData getBoat() 
	{
		return TyrannoBoatRegistry.getDataForBoat(this.entityData.get(BOAT_TYPE));
	}

	@Override
	public Packet<?> getAddEntityPacket() 
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
