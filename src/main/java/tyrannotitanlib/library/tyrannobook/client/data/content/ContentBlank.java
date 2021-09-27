package tyrannotitanlib.library.tyrannobook.client.data.content;

import java.util.ArrayList;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.client.data.element.TyrannobookElement;

@OnlyIn(Dist.CLIENT)
public class ContentBlank extends PageContent
{
	@Override
	public void build(TyrannobookData book, ArrayList<TyrannobookElement> list, boolean rightSide) { }
}
