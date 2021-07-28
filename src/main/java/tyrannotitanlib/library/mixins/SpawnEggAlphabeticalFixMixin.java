package tyrannotitanlib.library.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import tyrannotitanlib.library.base.util.TyrannoAlphabeticalItemGroupFiller;

@Mixin(SpawnEggItem.class)
public final class SpawnEggAlphabeticalFixMixin extends Item 
{
	private static final TyrannoAlphabeticalItemGroupFiller FILLER = TyrannoAlphabeticalItemGroupFiller.forClass(SpawnEggItem.class);

	private SpawnEggAlphabeticalFixMixin(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) 
	{
		if(this.allowdedIn(group)) 
		{
			ResourceLocation name = this.getRegistryName();
			if((name == null || !name.getNamespace().equals("minecraft")) && (group == ItemGroup.TAB_MISC || group == ItemGroup.TAB_SEARCH)) 
			{
				FILLER.fillItem(this, group, items);
			} 
			else 
			{
				super.fillItemCategory(group, items);
			}
		}
	}
}
