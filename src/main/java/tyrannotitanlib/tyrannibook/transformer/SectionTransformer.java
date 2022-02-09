package tyrannotitanlib.tyrannibook.transformer;

import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.tyrannibook.data.PageData;
import tyrannotitanlib.tyrannibook.data.SectionData;
import tyrannotitanlib.tyrannibook.data.TyrannobookData;
import tyrannotitanlib.tyrannibook.data.content.PageContent;

@RequiredArgsConstructor
public abstract class SectionTransformer extends TyrannobookTransformer {
	protected final String sectionName;

	@Override
	public final void transform(TyrannobookData book) {
		SectionData data = null;
		for (SectionData section : book.sections) {
			if (this.sectionName.equals(section.name)) {
				data = section;
				break;
			}
		}

		if (data != null) {
			transform(book, data);
		}
	}

	public abstract void transform(TyrannobookData book, SectionData section);

	protected PageData addPage(SectionData data, String name, ResourceLocation type, PageContent content) {
		PageData page = new PageData(true);
		page.source = data.source;
		page.parent = data;
		page.name = name;
		page.type = type;
		page.content = content;
		page.load();

		data.pages.add(page);

		return page;
	}
}
