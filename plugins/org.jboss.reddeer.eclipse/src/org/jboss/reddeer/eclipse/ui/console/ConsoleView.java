package org.jboss.reddeer.eclipse.ui.console;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
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
		// wait for text to appear
		new WaitWhile(new ConsoleHasText(""),TimePeriod.SHORT,false);
		return new DefaultStyledText().getText();
	}
	
	public void clearConsole() {
		log.info("Clearing console");
		new DefaultToolItem("Clear Console").click();
		new WaitUntil(new ConsoleHasText(""));
		log.info("Console cleared");
	}
	
	public void removeLaunch() {
		log.info("Removing launch from console");
		new DefaultToolItem("Remove Launch").click();
		log.info("Launch removed");
	}
	
	public void removeAllTerminatedLaunches() {
		log.info("Removing terminated launches from console");
		new DefaultToolItem("Remove All Terminated Launches").click();
		new WaitWhile(new ConsoleHasTextWidget());
		log.info("Terminated launches cleared");
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

	/**
	 * 
	 * This is not exactly a condition for checking if the console contains text.
	 * For this purpose use org.jboss.reddeer.eclipse.condition.ConsoleHasText
	 *
	 */
	private class ConsoleHasText implements WaitCondition {
		private String consoleText;

		public ConsoleHasText(String consoleText) {
			this.consoleText = consoleText;
		}

		@Override
		public boolean test() {
			try {
				DefaultStyledText dstConsole = new DefaultStyledText();
				return dstConsole.getText().equals(this.consoleText);
			} catch (SWTLayerException ex) {
				return false;
			}
		}

		@Override
		public String description() {
			return "Console text is not \"" + this.consoleText + "\"";
		}

	}
}
