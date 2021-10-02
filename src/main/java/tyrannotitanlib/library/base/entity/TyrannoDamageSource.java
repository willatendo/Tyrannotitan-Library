package tyrannotitanlib.library.base.entity;

import net.minecraft.util.DamageSource;

public class TyrannoDamageSource 
{
	public static DamageSource create(String id)
	{
		return new DamageSource(id);
	}
	
	public static DamageSource create(DamageSource source)
	{
		return source;
	}
}
