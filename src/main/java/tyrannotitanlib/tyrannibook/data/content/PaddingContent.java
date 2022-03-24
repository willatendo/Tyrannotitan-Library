package tyrannotitanlib.tyrannibook.data.content;

import static tyrannotitanlib.core.content.ModUtilities.TYRANNO_UTILS;

import java.util.Iterator;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import tyrannotitanlib.tyrannibook.data.PageData;
import tyrannotitanlib.tyrannibook.data.SectionData;
import tyrannotitanlib.tyrannibook.data.TyrannobookData;
import tyrannotitanlib.tyrannibook.transformer.TyrannobookTransformer;

@Getter
public abstract class PaddingContent extends BlankContent {
	public static final ResourceLocation LEFT_ID = TYRANNO_UTILS.mod("left_padding");
	public static final ResourceLocation RIGHT_ID = TYRANNO_UTILS.mod("right_padding");

	/** If true, this page is padding the left side, false pads the right side */
	public abstract boolean isLeft();

	/** Left variant */
	public static class ContentLeftPadding extends PaddingContent {
		@Override
		public boolean isLeft() {
			return true;
		}
	}

	/** Right variant */
	public static class ContentRightPadding extends PaddingContent {
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
					if (data.content instanceof PaddingContent && ((PaddingContent) data.content).isLeft() == previousLeft) {
						pageIterator.remove();
					} else {
						previousLeft = !previousLeft;
					}
				}
			}
		}
	}
}
