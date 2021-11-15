package tyrannotitanlib.library.base.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public interface IEntityData<T extends Entity> 
{
	void init(T entity, World world);

	default void init(T entity, World world, boolean init) 
	{
		init(entity, world);
	}

	void saveNBTData(CompoundNBT compound);
	
	void loadNBTData(CompoundNBT compound);

	String getID();
}