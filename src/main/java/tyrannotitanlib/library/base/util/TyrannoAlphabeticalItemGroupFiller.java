package tyrannotitanlib.library.base.util;

import java.util.function.Predicate;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import tyrannotitanlib.library.base.util.interfaces.IItemGroupFiller;

/*
 * Makes it so the spawn eggs appear correctly in the misc tab. (@TeamAbnormals)
 */

public class TyrannoAlphabeticalItemGroupFiller implements IItemGroupFiller 
{
	private final Predicate<Item> shouldInclude;

	public TyrannoAlphabeticalItemGroupFiller(Predicate<Item> shouldInclude) 
	{
		this.shouldInclude = shouldInclude;
	}

	public static <I extends Item> TyrannoAlphabeticalItemGroupFiller forClass(Class<I> clazz) 
	{
		return new TyrannoAlphabeticalItemGroupFiller(clazz::isInstance);
	}

	@Override
	public void fillItem(Item item, ItemGroup group, NonNullList<ItemStack> items) 
	{
		if(TyrannoItemStackUtil.isInGroup(item, group)) 
		{
			ResourceLocation location = item.getRegistryName();
			if(location != null) 
			{
				String itemName = location.getPath();
				int insert = -1;
				for(int i = 0; i < items.size(); i++) 
				{
					Item next = items.get(i).getItem();
					if(this.shouldInclude.test(next)) 
					{
						ResourceLocation nextName = next.getRegistryName();
						if(nextName == null || itemName.compareTo(nextName.getPath()) > 0) 
						{
							insert = i + 1;
						} 
						else if(insert == -1) 
						{
							insert += i + 1;
						} 
						else 
						{
							break;
						}
					}
				}
				if(insert == -1) 
				{
					items.add(new ItemStack(item));
				} 
				else 
				{
					items.add(insert, new ItemStack(item));
				}
			} 
			else 
			{
				items.add(new ItemStack(item));
			}
		}
	}
}
