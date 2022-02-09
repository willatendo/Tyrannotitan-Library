package tyrannotitanlib.tyrannibook.data;

import static tyrannotitanlib.core.content.Util.TYRANNO_UTILS;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.TrueCondition;
import tyrannotitanlib.tyrannibook.TyrannobookLoader;
import tyrannotitanlib.tyrannibook.data.content.ContentError;
import tyrannotitanlib.tyrannibook.data.content.PageContent;
import tyrannotitanlib.tyrannibook.data.element.IDataElement;
import tyrannotitanlib.tyrannibook.repository.BookRepository;

public class PageData implements IDataItem, IConditional {
	public String name = null;
	public ResourceLocation type = TYRANNO_UTILS.resource("blank");
	public String data = "";
	public float scale = 1.0F;
	public ICondition condition = TrueCondition.INSTANCE;

	public Map<ResourceLocation, JsonElement> extraData = Collections.emptyMap();

	public transient SectionData parent;
	public transient BookRepository source;
	public transient PageContent content;

	public PageData() {
		this(false);
	}

	public PageData(boolean custom) {
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
			this.name = "page" + this.parent.unnamedPageCounter++;
		}

		this.name = this.name.toLowerCase();

		Class<? extends PageContent> ctype = TyrannobookLoader.getPageType(this.type);

		if (!this.data.isEmpty() && !this.data.equals("no-load")) {
			Resource pageInfo = this.source.getResource(this.source.getResourceLocation(this.data));
			if (pageInfo != null) {
				String data = this.source.resourceToString(pageInfo);
				if (!data.isEmpty()) {
					PageTypeOverrider overrider = TyrannobookLoader.getGson().fromJson(data, PageTypeOverrider.class);
					if (overrider.type != null) {
						Class<? extends PageContent> overriddenType = TyrannobookLoader.getPageType(overrider.type);
						if (overriddenType != null) {
							ctype = TyrannobookLoader.getPageType(overrider.type);
							this.type = overrider.type;
						}
					}

					if (ctype != null) {
						try {
							this.content = TyrannobookLoader.getGson().fromJson(data, ctype);
						} catch (Exception e) {
							this.content = new ContentError("Failed to create a page of type \"" + this.type + "\", perhaps the page file \"" + this.data + "\" is missing or invalid?", e);
							e.printStackTrace();
						}
					} else {
						this.content = new ContentError("Failed to create a page of type \"" + this.type + "\" as it is not registered.");
					}
				}
			}
		}

		if (this.content == null) {
			if (ctype != null) {
				try {
					this.content = ctype.getDeclaredConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NullPointerException | NoSuchMethodException | InvocationTargetException e) {
					this.content = new ContentError("Failed to create a page of type \"" + this.type + "\".", e);
					e.printStackTrace();
				}
			} else {
				this.content = new ContentError("Failed to create a page of type \"" + this.type + "\" as it is not registered.");
			}
		}

		try {
			this.content.parent = this;
			this.content.load();
		} catch (Exception e) {
			this.content = new ContentError("Failed to load page " + this.parent.name + "." + this.name + ".", e);
			e.printStackTrace();
		}

		this.content.source = this.source;

		for (Field f : this.content.getClass().getFields()) {
			this.processField(f);
		}
	}

	private void processField(Field f) {
		f.setAccessible(true);

		if (Modifier.isTransient(f.getModifiers()) || Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers())) {
			return;
		}

		try {
			Object o = f.get(this.content);
			if (o != null) {
				this.processObject(o, 0);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void processObject(@Nullable Object o, final int depth) {
		if (depth > 4 || o == null)
			return;

		Class<?> c = o.getClass();
		boolean isArray = c.isArray();

		if (!isArray) {
			if (IDataElement.class.isAssignableFrom(c)) {
				((IDataElement) o).load(this.source);
			}
			return;
		}

		for (int i = 0; i < Array.getLength(o); i++) {
			this.processObject(Array.get(o, i), depth + 1);
		}
	}

	public String getTitle() {
		String title = this.parent.parent.strings.get(this.parent.name + "." + this.name);
		if (title != null) {
			return title;
		}
		title = content.getTitle();
		if (title != null && !title.isEmpty()) {
			return title;
		}
		return this.name;
	}

	@Override
	public boolean isConditionMet() {
		return condition.test();
	}

	private static class PageTypeOverrider {
		public ResourceLocation type;
	}
}
