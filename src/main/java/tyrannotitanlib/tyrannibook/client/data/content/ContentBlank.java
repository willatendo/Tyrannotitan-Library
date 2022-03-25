package tyrannotitanlib.tyrannibook.client.data.content;

import java.util.ArrayList;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.tyrannibook.client.data.TyrannobookData;
import tyrannotitanlib.tyrannibook.client.data.element.TyrannobookElement;

@OnlyIn(Dist.CLIENT)
public class ContentBlank extends PageContent
{
	@Override
	public void build(TyrannobookData book, ArrayList<TyrannobookElement> list, boolean rightSide) { }
}
