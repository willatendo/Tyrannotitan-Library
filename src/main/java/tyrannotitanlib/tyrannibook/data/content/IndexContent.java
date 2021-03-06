package tyrannotitanlib.tyrannibook.data.content;

import static tyrannotitanlib.core.content.ModUtilities.LOG;
import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.tyrannibook.data.TyrannobookData;
import tyrannotitanlib.tyrannibook.screen.book.element.BookElement;
import tyrannotitanlib.tyrannibook.transformer.IndexTransformer;

public class IndexContent extends ListingContent {
	public static final transient ResourceLocation ID = TYRANNO_UTILS.mod("index");

	private transient boolean loaded = false;
	private String[] hidden;
	private Operation[] operations;

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		if (!loaded) {
			loaded = true;
			Set<String> hiddenSet = hidden == null ? ImmutableSet.of("hidden") : ImmutableSet.<String>builder().add("hidden").add(hidden).build();
			Operation[] operations = Objects.requireNonNullElse(this.operations, new Operation[0]);
			parent.parent.pages.forEach(page -> {
				if (page != parent && !IndexTransformer.isPageHidden(page) && !hiddenSet.contains(page.name)) {
					for (Operation operation : operations) {
						if (page.name.equals(operation.before)) {
							switch (operation.action) {
							case "add_group" -> addEntry(operation.data, null, true);
							case "column_break" -> addColumnBreak();
							case "line_break" -> addEntry("", null, false);
							default -> LOG.error("Unknown ContentIndex action " + operation.action);
							}
						}
					}
					addEntry(page.getTitle(), page, page.name.startsWith("group_"));
				}
			});
		}
		super.build(book, list, rightSide);
	}

	protected static final class Operation {
		private final String before;
		private final String action;
		private final String data;

		public Operation(String before, String action, String data) {
			this.before = before;
			this.action = action;
			this.data = data;
		}
	}
}
