package tyrannotitanlib.content;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;
import tyrannotitanlib.library.TyrannotitanMod;
import tyrannotitanlib.library.base.utils.TyrannoUtils;

@Mod(TyrannoUtils.TYRANNO_ID)
public class TyrannotitanLibrary 
{
	public TyrannotitanLibrary() 
	{
		TyrannotitanMod.initBase(TyrannoUtils.TYRANNO_ID);
		TyrannotitanMod.initTyrannomation();
		TyrannotitanMod.createModBook("tyrannotitan", ItemGroup.TAB_MISC);
	}
}
