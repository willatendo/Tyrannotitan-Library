package tyrannotitanlib.content.server.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tyrannotitanlib.library.tyrannores.block.CopperOreBlock;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class TyrannoBlocks 
{
	public static final Block COPPER_ORE = TyrannoRegistries.create("copper_ore", new CopperOreBlock(AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F).randomTicks(), 1));

	public static void init() { TyrannoUtils.LOGGER.debug("Registering Tyranno Blocks"); }
}
