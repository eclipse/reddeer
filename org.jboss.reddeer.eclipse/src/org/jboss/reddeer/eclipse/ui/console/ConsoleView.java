package org.jboss.reddeer.eclipse.ui.console;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
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
		/**
		 * how it should look:
		 * 
		 * return new StyledText().getText();
		 * 
		 * there will not be any try-catch, because constructor 
		 * StyledText should solve this
		 */
		
		String consoleText = null;
		try {
			consoleText = viewObject.bot().styledText().getText();
		} catch (WidgetNotFoundException wnfe) {
			log.warn("There is no text in Console view");
			consoleText = null;
		}
		return consoleText;
	}
	
	public void clearConsole() {
		log.info("Clearing console");
		new ViewToolItem("Clear Console").click();
		log.info("Console cleared");
	}
	
}
