package tyrannibook.action.protocol;

import tyrannibook.screen.book.BookScreen;

public abstract class ActionProtocol {
	public abstract void processCommand(BookScreen book, String param);
}
