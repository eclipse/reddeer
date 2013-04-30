package org.jboss.reddeer.eclipse.ui.console;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

/**
 * Represents Console view in Eclipse
 * 
 * @author jjankovi
 *
 */
public class ConsoleView extends WorkbenchView {

	public ConsoleView() {
		super("Console");
	}
	
	public String getConsoleText() {
		new WaitUntil(new ConsoleHasTextWidget());
		return new DefaultStyledText().getText();
	}
	
	public void clearConsole() {
		log.info("Clearing console");
		new ViewToolItem("Clear Console").click();
		log.info("Console cleared");
	}
	
	private class ConsoleHasTextWidget implements WaitCondition{

		@Override
		public boolean test() {
			try{
				new DefaultStyledText();
			}catch(SWTLayerException ex){
				return false;
			}
			return true;
		}

		@Override
		public String description() {
			return "Console has no text";
		}
		
	}
	
}
