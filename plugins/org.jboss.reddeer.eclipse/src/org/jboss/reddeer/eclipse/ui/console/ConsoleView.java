package org.jboss.reddeer.eclipse.ui.console;

import static org.hamcrest.CoreMatchers.containsString;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Represents Console view in Eclipse
 * 
 * @author jjankovi
 *
 */
public class ConsoleView extends WorkbenchView {

	/**
	 * Constructs the view with "Console".
	 */
	public ConsoleView() {
		super("Console");
	}
	
	/**
	 * Returns console test.
	 * 
	 * @return Console text
	 */
	public String getConsoleText() {
		activate();
		new WaitUntil(new ConsoleHasTextWidget());
		// wait for text to appear
		new WaitWhile(new ConsoleHasText(""),TimePeriod.SHORT,false);
		return new DefaultStyledText().getText();
	}
	
	/**
	 * Clears the console.
	 */
	public void clearConsole() {
		log.info("Clearing console");
		activate();		
		new ViewToolItem("Clear Console").click();
		new WaitUntil(new ConsoleHasText(""));
		log.info("Console cleared");
	}
	
	/**
	 * Removes a launch from the console.
	 */
	public void removeLaunch() {
		log.info("Removing launch from console");
		activate();
		new ViewToolItem("Remove Launch").click();
		log.info("Launch removed");
	}
	
	/**
	 * Removes all terminated launches.
	 */
	public void removeAllTerminatedLaunches() {
		log.info("Removing terminated launches from console");
		activate();
		new ViewToolItem("Remove All Terminated Launches").click();
		new WaitWhile(new ConsoleHasTextWidget());
		log.info("Terminated launches cleared");
	}
	
	/**
	 * Terminates an active console via the tool item <i>Terminate</i>. 
	 */
	public void terminateConsole() {
		log.info("Terminating console");
		activate();
		ViewToolItem terminate = new ViewToolItem("Terminate");
		if (terminate.isEnabled()) {
			terminate.click();
			// wait till the "<terminated" label appears
			new DefaultLabel(new WithTextMatcher(containsString("<terminated>")));
			log.info("Console terminated");
		} else {
			log.info("Console was terminated earlier");
		}
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
			return "console has styled text";
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
			return "console text is \"" + this.consoleText + "\"";
		}

	}
}
