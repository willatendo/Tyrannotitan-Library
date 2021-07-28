package tyrannotitanlib.library.base.util;

import javax.annotation.Nonnull;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import tyrannotitanlib.library.mixins.IItemInvokerMixin;

public class TyrannoItemStackUtil 
{
	private static final String[] M_NUMERALS = { "", "M", "MM", "MMM" };
	private static final String[] C_NUMERALS = { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" };
	private static final String[] X_NUMERALS = { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" };
	private static final String[] I_NUMERALS = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" };

	public static int findIndexOfItem(Item item, NonNullList<ItemStack> items) 
	{
		for(int i = 0; i < items.size(); i++) 
		{
			if(items.get(i).getItem() == item) 
			{
				return i;
			}
		}
		return -1;
	}

	public static void fillAfterItemForGroup(Item item, Item targetItem, ItemGroup group, NonNullList<ItemStack> items) 
	{
		if(isInGroup(item, group)) 
		{
			int targetIndex = findIndexOfItem(targetItem, items);
			if(targetIndex != -1) 
			{
				items.add(targetIndex + 1, new ItemStack(item));
			} 
			else 
			{
				items.add(new ItemStack(item));
			}
		}
	}

	public static String intToRomanNumerals(int number) 
	{
		String thousands = M_NUMERALS[number / 1000];
		String hundreds = C_NUMERALS[(number % 1000) / 100];
		String tens = X_NUMERALS[(number % 100) / 10];
		String ones = I_NUMERALS[number % 10];
		return thousands + hundreds + tens + ones;
	}

	public static boolean isInGroup(Item item, @Nonnull ItemGroup group) 
	{
		return ((IItemInvokerMixin) item).callAllowdedIn(group);
	}
}
