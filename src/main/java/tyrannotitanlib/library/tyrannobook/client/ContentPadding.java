package tyrannotitanlib.library.tyrannobook.client;

import tyrannotitanlib.library.tyrannobook.client.data.content.BlankContent;

public abstract class ContentPadding extends BlankContent {
	public static final String LEFT_ID = "left_padding";
	public static final String RIGHT_ID = "right_padding";

	public abstract boolean isLeft();

	public static class ContentLeftPadding extends ContentPadding {
		@Override
		public boolean isLeft() {
			return true;
		}
	}

	public static class ContentRightPadding extends ContentPadding {
		@Override
		public boolean isLeft() {
			return false;
		}
	}
}
