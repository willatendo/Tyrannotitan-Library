package core.library.tyrannotitanlib.block;

import com.google.common.collect.Maps;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.common.util.NonNullSupplier;

public class TyrannoLogBlock extends RotatedPillarBlock {
	public static NonNullSupplier<Block> strippedLogBlock;
	public static Block logBlock;

	public TyrannoLogBlock(NonNullSupplier<Block> strippedLog, Properties properties) {
		super(properties);
		logBlock = this;
		strippedLogBlock = strippedLog;
	}

	public static void addStripping() {
		AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
		AxeItem.STRIPPABLES.put(logBlock, strippedLogBlock.get());
	}
}
