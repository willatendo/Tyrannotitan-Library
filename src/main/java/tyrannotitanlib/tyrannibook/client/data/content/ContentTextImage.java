package tyrannotitanlib.tyrannibook.client.data.content;

import java.util.ArrayList;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.tyrannibook.client.data.TyrannobookData;
import tyrannotitanlib.tyrannibook.client.data.element.ImageData;
import tyrannotitanlib.tyrannibook.client.data.element.ImageElement;
import tyrannotitanlib.tyrannibook.client.data.element.TextData;
import tyrannotitanlib.tyrannibook.client.data.element.TextElement;
import tyrannotitanlib.tyrannibook.client.data.element.TyrannobookElement;
import tyrannotitanlib.tyrannibook.client.screen.TyrannobookScreen;

@OnlyIn(Dist.CLIENT)
public class ContentTextImage extends PageContent 
{
	public String title = null;
	public TextData[] text;
	public ImageData image;

	@Override
	public void build(TyrannobookData book, ArrayList<TyrannobookElement> list, boolean rightSide) 
	{
		int y = TITLE_HEIGHT;

		if(this.title == null || this.title.isEmpty()) 
		{
			y = 0;
		} 
		else 
		{
			this.addTitle(list, this.title);
		}

		if(this.title == null || this.title.isEmpty()) 
		{
			y = 0;
		} 
		else 
		{
			this.addTitle(list, this.title);
		}

		if(this.text != null && this.text.length > 0) 
		{
			list.add(new TextElement(0, y, TyrannobookScreen.PAGE_WIDTH, TyrannobookScreen.PAGE_HEIGHT - 105, this.text));
		}

		if(this.image != null && this.image.location != null) 
		{
			list.add(new ImageElement(0, y + TyrannobookScreen.PAGE_HEIGHT - 100, TyrannobookScreen.PAGE_WIDTH, 100 - y, this.image));
		} 
		else 
		{
			list.add(new ImageElement(0, y + TyrannobookScreen.PAGE_HEIGHT - 100, TyrannobookScreen.PAGE_WIDTH, 100 - y, ImageData.MISSING));
		}
	}
}