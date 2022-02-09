package tyrannotitanlib.tyrannibook.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;

import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.TrueCondition;
import tyrannotitanlib.tyrannibook.TyrannobookLoader;
import tyrannotitanlib.tyrannibook.data.content.ContentError;
import tyrannotitanlib.tyrannibook.data.element.ImageData;
import tyrannotitanlib.tyrannibook.repository.BookRepository;
import tyrannotitanlib.tyrannibook.screen.book.BookScreen;

public class SectionData implements IDataItem, IConditional {

	public String name = null;
	public ImageData icon = new ImageData();
	public Set<String> requirements = Sets.newHashSet();
	public boolean hideWhenLocked = false;
	public String data = "";
	public ICondition condition = TrueCondition.INSTANCE;

	public Map<ResourceLocation, JsonElement> extraData = Collections.emptyMap();

	public transient int unnamedPageCounter = 0;
	public transient TyrannobookData parent;
	public transient BookRepository source;
	public transient ArrayList<PageData> pages = new ArrayList<>();

	public SectionData() {
		this(false);
	}

	public SectionData(boolean custom) {
		if (custom) {
			this.data = "no-load";
		}
	}

	public String translate(String string) {
		return this.parent.translate(string);
	}

	@Override
	public void load() {
		if (this.name == null) {
			this.name = "section" + this.parent.unnamedSectionCounter++;
		}

		this.name = this.name.toLowerCase();

		if (!this.data.equals("no-load")) {
			Resource pagesInfo = this.source.getResource(this.source.getResourceLocation(this.data));
			if (pagesInfo != null) {
				String data = this.source.resourceToString(pagesInfo);
				if (!data.isEmpty()) {
					try {
						this.pages = this.getPages(data);
					} catch (Exception e) {
						this.pages = new ArrayList<>();
						PageData pdError = new PageData(true);
						pdError.name = "errorrenous";
						pdError.content = new ContentError("Failed to load section " + this.name + ".", e);
						this.pages.add(pdError);

						e.printStackTrace();
					}
				}
			}
		}

		for (PageData page : this.pages) {
			page.parent = this;
			page.source = this.source;
			page.load();
		}

		this.icon.load(this.source);
	}

	protected ArrayList<PageData> getPages(String data) {
		List<PageData> pages = Arrays.asList(TyrannobookLoader.getGson().fromJson(data, PageData[].class));

		return pages.stream().filter(PageData::isConditionMet).collect(Collectors.toCollection(ArrayList::new));
	}

	public void update(@Nullable BookScreen.AdvancementCache advancementCache) {
	}

	public String getTitle() {
		String title = this.parent.strings.get(this.name);
		return title == null ? this.name : title;
	}

	public int getPageCount() {
		return this.pages.size();
	}

	public boolean isUnlocked(@Nullable BookScreen.AdvancementCache advancementCache) {
		if (advancementCache == null || this.requirements == null || this.requirements.size() == 0) {
			return true;
		}

		for (String achievement : this.requirements) {
			if (!requirementSatisfied(achievement, advancementCache)) {
				return false;
			}
		}

		return true;
	}

	public static boolean requirementSatisfied(String requirement, @Nullable BookScreen.AdvancementCache advancementCache) {
		if (advancementCache == null) {
			return true;
		}

		AdvancementProgress progress = advancementCache.getProgress(requirement);

		return progress != null && progress.isDone();

	}

	@Override
	public boolean isConditionMet() {
		return condition.test();
	}
}
