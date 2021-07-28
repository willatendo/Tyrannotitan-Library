package tyrannotitanlib.library.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

@Mixin(Item.class)
public interface IItemInvokerMixin 
{
	@Invoker
	boolean callAllowdedIn(ItemGroup group);
}
