package tyrannotitanlib.tyrannibook.action.protocol;

import tyrannotitanlib.tyrannibook.screen.book.BookScreen;

public abstract class ActionProtocol {
	public abstract void processCommand(BookScreen book, String param);
}
