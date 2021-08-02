package tyrannotitanlib.library.base.block.builder;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class Builder 
{
	public static Properties simple(Material material, MaterialColor colour)
	{
		return Properties.of(material, colour);
	}
}
