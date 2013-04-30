package org.jboss.reddeer.swt.condition;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.jboss.reddeer.swt.util.Bot;

public class ShellIsActive implements WaitCondition{
	
    private SWTBot bot;
    
    public ShellIsActive(){
    	bot = Bot.get();
    }
	@Override
	public boolean test() {
		SWTBotShell activeShell = bot.activeShell();
		return activeShell!= null;
	}

	@Override
	public String description() {
		return "Shell is active";
	}
}