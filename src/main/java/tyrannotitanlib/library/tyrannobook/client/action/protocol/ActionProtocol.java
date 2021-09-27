package tyrannotitanlib.library.tyrannobook.client.action.protocol;

import tyrannotitanlib.library.tyrannobook.client.screen.TyrannobookScreen;

public abstract class ActionProtocol 
{
	public final String protocol;

	public ActionProtocol(String protocol) 
	{
		this.protocol = protocol;
	}

	public abstract void processCommand(TyrannobookScreen book, String param);
}
