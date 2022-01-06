package tyrannotitanlib.library.tyrannobook.transformer;

import javax.annotation.Nullable;

import tyrannotitanlib.library.tyrannobook.data.PageData;
import tyrannotitanlib.library.tyrannobook.data.SectionData;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.content.ListingContent;

public class ContentListingSectionTransformer extends SectionTransformer {
	private final Boolean largeTitle;
	private final Boolean centerTitle;

	public ContentListingSectionTransformer(String sectionName, @Nullable Boolean largeTitle, @Nullable Boolean centerTitle) {
		super(sectionName);
		this.largeTitle = largeTitle;
		this.centerTitle = centerTitle;
	}

	public ContentListingSectionTransformer(String sectionName) {
		this(sectionName, null, null);
	}

	@Override
	public void transform(TyrannobookData book, SectionData data) {
		ListingContent listing = new ListingContent();
		listing.setLargeTitle(largeTitle);
		listing.setCenterTitle(centerTitle);
		listing.title = book.translate(sectionName);
		String subtextKey = sectionName + ".subtext";
		if (book.strings.containsKey(subtextKey)) {
			listing.subText = book.translate(subtextKey);
		}

		PageData listingPage = new PageData(true);
		listingPage.name = sectionName;
		listingPage.source = data.source;
		listingPage.parent = data;
		listingPage.content = listing;

		data.pages.removeIf(sectionPage -> !processPage(book, listing, sectionPage));
		if (listing.hasEntries()) {
			listingPage.load();
			data.pages.add(0, listingPage);
		}
	}

	protected boolean processPage(TyrannobookData book, ListingContent listing, PageData page) {
		if (!IndexTransformer.isPageHidden(page) && !page.name.equals("hidden")) {
			listing.addEntry(page.getTitle(), page);
		}
		return true;
	}
}
