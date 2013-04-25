package org.jboss.reddeer.eclipse.ui.console;

import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
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
		open();
		return new DefaultStyledText().getText();
	}
	
	public void clearConsole() {
		log.info("Clearing console");
		new ViewToolItem("Clear Console").click();
		log.info("Console cleared");
	}
	
}
