package tyrannotitanlib.library.tyrannobook.client.data.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tyrannotitanlib.library.tyrannobook.client.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.client.data.element.AnimationToggleElement;
import tyrannotitanlib.library.tyrannobook.client.data.element.StructureElement;
import tyrannotitanlib.library.tyrannobook.client.data.element.TextElement;
import tyrannotitanlib.library.tyrannobook.client.data.element.TyrannobookElement;
import tyrannotitanlib.library.tyrannobook.client.repository.TyrannobookRepository;
import tyrannotitanlib.library.tyrannobook.client.screen.ArrowButton;
import tyrannotitanlib.library.tyrannobook.client.screen.TyrannobookScreen;
import tyrannotitanlib.library.utils.TyrannoUtils;

@OnlyIn(Dist.CLIENT)
public class ContentStructure extends PageContent 
{
	public static final transient String ID = "structure";

	public String title;
	public String data;

	public String text;

	public final transient StructureTemplate template = new StructureTemplate();
	public transient List<StructureTemplate.StructureBlockInfo> templateBlocks = new ArrayList<>();

	@Override
	public void load() 
	{
		TyrannobookRepository repo = this.parent.source;

		if(this.data == null || this.data.isEmpty()) 
		{
			return;
		}

		ResourceLocation location = repo.getResourceLocation(this.data);
		Resource resource = repo.getResource(location);

		if (resource == null) 
		{
			return;
		}

		try 
		{
			CompoundTag compoundnbt = NbtIo.readCompressed(resource.getInputStream());
			this.template.load(compoundnbt);
		} 
		catch(IOException e) 
		{
			e.printStackTrace();
			return;
		}

		this.templateBlocks = this.template.palettes.get(0).blocks();

		for(int i = 0; i < this.templateBlocks.size(); i++) 
		{
			StructureTemplate.StructureBlockInfo info = this.templateBlocks.get(i);
			if(info.state == Blocks.AIR.defaultBlockState()) 
			{
				this.templateBlocks.remove(i);
				i--;
			} 
			else if(info.state.isAir())
				TyrannoUtils.LOGGER.error("Found non-default air block in template " + this.data);
		}
	}

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

		int offset = 0;
		int structureSizeX = TyrannobookScreen.PAGE_WIDTH;
		int structureSizeY = TyrannobookScreen.PAGE_HEIGHT - y - 10;

		if(!StringUtil.isNullOrEmpty(this.text)) 
		{
			offset = 15;
			structureSizeX -= 2 * offset;
			structureSizeY -= 2 * offset;

			list.add(new TextElement(0, TyrannobookScreen.PAGE_HEIGHT - 10 - 2 * offset, TyrannobookScreen.PAGE_WIDTH, 2 * offset, this.text));
		}

		if(this.template != null && this.template.getSize() != BlockPos.ZERO) 
		{
			boolean showButtons = this.template.getSize().getY() > 1;

			StructureElement structureElement = new StructureElement(offset, y, structureSizeX, structureSizeY, this.template, this.templateBlocks);
			list.add(structureElement);

			if(showButtons) 
			{
				int col = book.appearance.structureButtonColor;
				int colHover = book.appearance.structureButtonColorHovered;
				int colToggled = book.appearance.structureButtonColorToggled;

				list.add(new AnimationToggleElement(TyrannobookScreen.PAGE_WIDTH - ArrowButton.ArrowType.REFRESH.w, 0, ArrowButton.ArrowType.REFRESH, col, colHover, colToggled, structureElement));
			}
		}
	}
}
