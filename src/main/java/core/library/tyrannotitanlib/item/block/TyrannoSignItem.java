package core.library.tyrannotitanlib.item.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

public class TyrannoSignItem extends SignItem {
	public TyrannoSignItem(Properties properties, Block standingBlock, Block wallBlock) {
		super(properties, standingBlock, wallBlock);
	}

	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return 200;
	}
}
