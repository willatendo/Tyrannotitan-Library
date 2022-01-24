package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.Iterator;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.content.TyrannoUtils;
import tyrannotitanlib.library.tyrannobook.data.PageData;
import tyrannotitanlib.library.tyrannobook.data.SectionData;
import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.transformer.TyrannobookTransformer;

@Getter
public abstract class ContentPadding extends BlankContent {
	public static final ResourceLocation LEFT_ID = TyrannoUtils.rL("left_padding");
	public static final ResourceLocation RIGHT_ID = TyrannoUtils.rL("right_padding");

	/** If true, this page is padding the left side, false pads the right side */
	public abstract boolean isLeft();

	/** Left variant */
	public static class ContentLeftPadding extends ContentPadding {
		@Override
		public boolean isLeft() {
			return true;
		}
	}

	/** Right variant */
	public static class ContentRightPadding extends ContentPadding {
		@Override
		public boolean isLeft() {
			return false;
		}
	}

	/** Transformer to make this page type work */
	public static class PaddingBookTransformer extends TyrannobookTransformer {
		/** Regular transformer considering indexes */
		public static final PaddingBookTransformer INSTANCE = new PaddingBookTransformer();

		private PaddingBookTransformer() {
		}

		@Override
		public void transform(TyrannobookData bookData) {
			// first page is on the right side
			boolean previousLeft = true;

			// iterate through all pages, tracking whehter we are left or right
			for (SectionData section : bookData.sections) {
				Iterator<PageData> pageIterator = section.pages.iterator();
				while (pageIterator.hasNext()) {
					PageData data = pageIterator.next();
					// if its left and the current page is odd, or its right and the current page is
					// even, skip
					if (data.content instanceof ContentPadding && ((ContentPadding) data.content).isLeft() == previousLeft) {
						pageIterator.remove();
					} else {
						previousLeft = !previousLeft;
					}
				}
			}
		}
	}
}
