package org.jboss.reddeer.swt.condition;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.jboss.reddeer.swt.util.Bot;
/**
 * Condition is fulfilled when shell with text is active
 * @author Vlado Pakan
 *
 */
public class ShellWithTextIsActive implements WaitCondition{
	
	private final Logger log = Logger.getLogger(ShellWithTextIsActive.class);
	private String text;
    private SWTBot bot;
    
    public ShellWithTextIsActive(String text){
    	this.text = text;
    	bot = Bot.get();
    }
	@Override
	public boolean test() {
		SWTBotShell activeShell = bot.activeShell();
		if (activeShell != null 
			&& activeShell.getText() != null
			&& activeShell.getText().equals(text)){
			log.debug("Shell with text " + text + " is active");
			return true;
		}
		else{
			log.debug("Shell with text " + text + " is not active");
			return false;
		}
	}

	@Override
	public String description() {
		return "Shell with text " + text + " is active";
	}
}
