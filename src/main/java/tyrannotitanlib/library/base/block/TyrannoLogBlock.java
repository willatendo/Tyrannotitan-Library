package tyrannotitanlib.library.base.block;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.AxeItem;
import net.minecraftforge.common.util.NonNullSupplier;

public class TyrannoLogBlock extends RotatedPillarBlock
{
	public static NonNullSupplier<Block> strippedLogBlock;
	public static Block logBlock;
	
	public TyrannoLogBlock(NonNullSupplier<Block> strippedLog, Properties properties) 
	{
		super(properties);
		logBlock = this;
		strippedLogBlock = strippedLog;
	}
	
	public static void addStripping()
	{
		AxeItem.STRIPABLES = Maps.newHashMap(AxeItem.STRIPABLES);
		AxeItem.STRIPABLES.put(logBlock, strippedLogBlock.get());
	}
}
