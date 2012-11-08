package org.jboss.tools.mylyn.ui.bot.test;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.condition.WaitCondition;
/**
 * Condition is fulfilled when button with text is active
 * @author Vlado Pakan / Len DiMaggio
 *
 */
public class ButtonWithTextIsActive implements WaitCondition {
	
    private String text;
    private SWTBot bot;
    
    public ButtonWithTextIsActive(String text){
    	this.text = text;
    	bot = Bot.get();
 	}

	@Override
	public boolean test() {
		
		if (new PushButton(text).isEnabled() ) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String description() {
		return "Button with text " + text + " is active";
	}

}
