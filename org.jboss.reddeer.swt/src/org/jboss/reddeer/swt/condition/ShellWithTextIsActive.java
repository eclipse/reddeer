package org.jboss.reddeer.swt.condition;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.ShellLookup;

/**
 * Condition is fulfilled when shell with text is active
 * @author Vlado Pakan
 *
 */
public class ShellWithTextIsActive implements WaitCondition{
	
	private String text;
	private final Logger log = Logger.getLogger(this.getClass());
    
    public ShellWithTextIsActive(String text){
    	this.text = text;
    }
	@Override
	public boolean test() {
		boolean result = false;
		Shell activeShell = new ShellLookup().getCurrentActiveShell();
		log.debug("Active shell : " + activeShell);		
		if (activeShell != null){
			String shellText = WidgetHandler.getInstance().getText(activeShell);
			log.debug("Active shell text: " + shellText);			
			result = shellText != null && shellText.equals(text);
		}
		return result;
	}

	@Override
	public String description() {
		return "Shell with text " + text + " is active";
	}
}
