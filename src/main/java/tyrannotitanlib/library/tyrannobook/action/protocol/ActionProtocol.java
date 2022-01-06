package tyrannotitanlib.library.tyrannobook.action.protocol;

import tyrannotitanlib.library.tyrannobook.screen.book.BookScreen;

public abstract class ActionProtocol {
	public abstract void processCommand(BookScreen book, String param);
}
