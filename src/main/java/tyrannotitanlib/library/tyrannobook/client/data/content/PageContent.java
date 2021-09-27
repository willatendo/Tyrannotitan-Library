package tyrannotitanlib.library.tyrannobook.client.data.content;

import java.util.ArrayList;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.library.tyrannobook.client.data.PageData;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.client.data.element.TextData;
import tyrannotitanlib.library.tyrannobook.client.data.element.TextElement;
import tyrannotitanlib.library.tyrannobook.client.data.element.TyrannobookElement;
import tyrannotitanlib.library.tyrannobook.client.repository.TyrannobookRepository;
import tyrannotitanlib.library.tyrannobook.client.screen.TyrannobookScreen;

@OnlyIn(Dist.CLIENT)
public abstract class PageContent 
{
	public static final transient int TITLE_HEIGHT = 16;
	
	public transient PageData parent;
	public transient TyrannobookRepository source;
	
	public void load() { }
	
	public abstract void build(TyrannobookData book, ArrayList<TyrannobookElement> list, boolean rightSide);
	
	public void addTitle(ArrayList<TyrannobookElement> list, String title) 
	{
		TextData tdTitle = new TextData(title);
		tdTitle.underlined = true;
		this.addTitle(list, new TextData[]{tdTitle});
	}
	
	public void addTitle(ArrayList<TyrannobookElement> list, TextData[] title) 
	{
		list.add(new TextElement(0, 0, TyrannobookScreen.PAGE_WIDTH, 9, title));
	}
}
