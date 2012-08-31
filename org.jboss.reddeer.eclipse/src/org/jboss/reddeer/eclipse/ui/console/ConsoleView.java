package org.jboss.reddeer.eclipse.ui.console;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.jboss.reddeer.swt.condition.AllRunningJobsAreNotActive;
import org.jboss.reddeer.swt.wait.WaitUntilCondition;
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
		 * open();
		 * return new StyledText().getText();
		 * 
		 * there will not be any try-catch, because constructor 
		 * StyledText should solve this
		 */
		
		open();
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
		
		/**
		 * how it should look:
		 * 
		 * open();
		 * new ToolbarButton("Clear Console").click();
		 * 
		 * if toolbarbutton is not enabled, therefore clicking on it wont work
		 * this situation should be solved in lower layer. Also there will not 
		 * be any try-catch, because constructor ToolbarButton should solve this 
		 * either
		 */
		
		open();
		log.info("Clearing console");
		try {
			SWTBotToolbarButton button = getToolBar("Clear Console");
			if (button.isEnabled()) {
				button.click();
				new WaitUntilCondition(
						new AllRunningJobsAreNotActive(), 30000);
				log.info("Console was cleared");
			} else {
				log.warn("Console was not cleared, button is not enabled");
			}
		} catch (WidgetNotFoundException wnfe) {
			log.warn("There is no 'Clear console' button in Console view");
		}
		
	}
	
}
