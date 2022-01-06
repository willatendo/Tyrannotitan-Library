package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.tyrannobook.transformer.IndexTransformer;
import tyrannotitanlib.library.utils.TyrannoUtils;

public class IndexContent extends ListingContent {
	public static final transient ResourceLocation ID = TyrannoUtils.rL("index");

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
							default -> TyrannoUtils.LOGGER.error("Unknown ContentIndex action " + operation.action);
							}
						}
					}
					addEntry(page.getTitle(), page, page.name.startsWith("group_"));
				}
			});
		}
		super.build(book, list, rightSide);
	}

	@RequiredArgsConstructor
	protected static final class Operation {
		private final String before;
		private final String action;
		private final String data;
	}
}