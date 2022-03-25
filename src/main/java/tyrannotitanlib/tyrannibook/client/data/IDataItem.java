package tyrannotitanlib.tyrannibook.client.data;

import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IDataItem 
{
	void load();
}
