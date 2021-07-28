package tyrannotitanlib.library.tyrannomation.world.storage;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class TyrannoLibIdTracker extends WorldSavedData 
{
	private static final String NAME = "tyranno_ids";
	private final Object2IntMap<String> usedIds = new Object2IntOpenHashMap<>();

	public TyrannoLibIdTracker() 
	{
		super(NAME);
		this.usedIds.defaultReturnValue(-1);
	}

	public static TyrannoLibIdTracker from(ServerWorld world) 
	{
		return world.getServer().overworld().getDataStorage().computeIfAbsent(TyrannoLibIdTracker::new, NAME);
	}

	@Override
	public void load(CompoundNBT tag) 
	{
		this.usedIds.clear();
		for(String key : tag.getAllKeys()) 
		{
			if(tag.contains(key, 99)) 
			{
				this.usedIds.put(key, tag.getInt(key));
			}
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) 
	{
		for(Object2IntMap.Entry<String> id : this.usedIds.object2IntEntrySet()) 
		{
			tag.putInt(id.getKey(), id.getIntValue());
		}
		return tag;
	}

	public int getNextId(Type type) 
	{
		final int id = this.usedIds.getInt(type.key) + 1;
		this.usedIds.put(type.key, id);
		this.setDirty();
		return id;
	}

	public enum Type 
	{
		ITEM("Item");

		private final String key;

		Type(String key) 
		{
			this.key = key;
		}
	}
}
