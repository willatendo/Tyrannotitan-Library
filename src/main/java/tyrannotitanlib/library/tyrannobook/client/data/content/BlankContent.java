package tyrannotitanlib.library.tyrannobook.client.data.content;

import java.util.ArrayList;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.client.data.element.TyrannobookElement;
import tyrannotitanlib.library.utils.TyrannoUtils;

@OnlyIn(Dist.CLIENT)
public class BlankContent extends PageContent {
	public static final ResourceLocation ID = TyrannoUtils.rL("blank");
	
	@Override
	public void build(TyrannobookData book, ArrayList<TyrannobookElement> list, boolean rightSide) {
	}
}
