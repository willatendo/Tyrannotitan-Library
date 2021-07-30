package tyrannotitanlib.library.base.block;

import java.util.function.Supplier;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.AxeItem;

public class TyrannoLogBlock extends RotatedPillarBlock
{
	public static Supplier<Block> strippedLogBlock;
	public static Block logBlock;
	
	public TyrannoLogBlock(Supplier<Block> strippedLog, Properties properties) 
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
