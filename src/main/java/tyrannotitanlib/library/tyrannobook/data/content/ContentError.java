package tyrannotitanlib.library.tyrannobook.data.content;

import java.util.ArrayList;

import javax.annotation.Nullable;

import tyrannotitanlib.library.tyrannobook.data.TyrannobookData;
import tyrannotitanlib.library.tyrannobook.data.BookLoadException;
import tyrannotitanlib.library.tyrannobook.data.element.TextData;
import tyrannotitanlib.library.tyrannobook.screen.book.BookScreen;
import tyrannotitanlib.library.tyrannobook.screen.book.element.BookElement;
import tyrannotitanlib.library.tyrannobook.screen.book.element.TextElement;

public class ContentError extends PageContent {

	private final String errorStage;
	@Nullable
	private final Exception exception;

	public ContentError(String errorStage) {
		this(errorStage, null);
	}

	public ContentError(String errorStage, @Nullable Exception e) {
		this.errorStage = errorStage;
		this.exception = e;
	}

	@Override
	public void build(TyrannobookData book, ArrayList<BookElement> list, boolean rightSide) {
		this.addTitle(list, "Error");

		if (exception instanceof BookLoadException) {
			buildSimple(list);
			return;
		}

		StackTraceElement[] stackTrace = null;
		if (this.exception != null) {
			stackTrace = this.exception.getStackTrace();
		}

		TextData[] text = new TextData[1 + (this.exception != null ? 2 : 0) + (stackTrace != null ? 1 + Math.min(stackTrace.length * 2, 8) : 0)];
		text[0] = new TextData(this.errorStage);
		text[0].color = "dark_red";
		text[0].underlined = true;
		text[0].paragraph = true;

		if (this.exception != null) {
			text[1] = new TextData("The following error has occured:");
			text[1].color = "dark_red";
			text[1].paragraph = true;

			text[2] = new TextData(this.exception.getMessage() != null ? this.exception.getMessage() : this.exception.getClass().getSimpleName());
			text[2].color = "dark_red";
			text[2].paragraph = true;

			text[3] = TextData.LINEBREAK;
		}

		if (stackTrace != null) {
			for (int i = 0; i < stackTrace.length && 5 + i * 2 < text.length; i++) {
				text[4 + i * 2] = new TextData(stackTrace[i].toString());
				text[4 + i * 2].text += "\n";
				text[4 + i * 2].color = "dark_red";
				text[5 + i * 2] = TextData.LINEBREAK;
			}
		}

		list.add(new TextElement(0, TITLE_HEIGHT, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - TITLE_HEIGHT, text));
	}

	public void buildSimple(ArrayList<BookElement> list) {
		TextData[] text = new TextData[1];

		text[0] = new TextData(exception.getMessage());
		text[0].color = "dark_red";

		list.add(new TextElement(0, TITLE_HEIGHT, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - TITLE_HEIGHT, text));
	}
}
