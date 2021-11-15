package tyrannotitanlib.library.base.entity;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public abstract class TyrannoEntityProperties<T extends Entity> implements IEntityData<T> 
{
	private World world;
	private T entity;
	private Set<PropertiesTracker<?>> trackers = Collections.newSetFromMap(new WeakHashMap<>());

	@Override
	public final void init(T entity, World world) 
	{
		this.entity = entity;
		this.world = world;
		this.init();
	}
	
	public Set<PropertiesTracker<?>> getTrackers() 
	{
		return this.trackers;
	}
	
	public void sync() 
	{
		this.trackers.forEach(PropertiesTracker::setReady);
	}
	
	public final World getLevel() 
	{
		return this.world;
	}
	
	public final T getEntity() 
	{
		return this.entity;
	}

	public abstract void init();

	@Override
	public abstract String getID();

	public abstract Class<T> getEntityClass();

	public int getTrackingTime() 
	{
		return -1;
	}

	public int getTrackingUpdateTime() 
	{
		return 0;
	}

	public void saveTrackingSensitiveData(CompoundNBT compound) 
	{
		this.saveNBTData(compound);
	}
	
	public void loadTrackingSensitiveData(CompoundNBT compound) 
	{
		this.loadNBTData(compound);
	}
	
	public void onSync() { }
	
	public PropertiesTracker<T> createTracker(T entity) 
	{
		PropertiesTracker<T> tracker = new PropertiesTracker<>(entity, this);
		this.trackers.add(tracker);
		return tracker;
	}
}