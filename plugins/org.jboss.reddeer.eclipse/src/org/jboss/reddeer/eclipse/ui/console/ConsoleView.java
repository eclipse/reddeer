package org.jboss.reddeer.eclipse.ui.console;

import static org.hamcrest.CoreMatchers.containsString;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.eclipse.condition.ConsoleHasLaunch;
import org.jboss.reddeer.eclipse.condition.ConsoleIsTerminated;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.label.DefaultLabel;
import org.jboss.reddeer.swt.impl.menu.ToolItemMenu;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
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
		new WaitWhile(new ConsoleHasLaunch());
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
			new WaitUntil(new ConsoleIsTerminated());
			log.info("Console terminated");
		} else {
			log.info("Console was terminated earlier");
		}
	}
	
	/**
	 * Toggles the button indicating if the view should be activated on standard output change
	 * @param toggle
	 */
	public void toggleShowConsoleOnStandardOutChange(boolean toggle){
		activate();
		new ViewToolItem("Show Console When Standard Out Changes").toggle(toggle);
	}
	
	/**
	 * Returns true when console has launch.
	 * 
	 * @return
	 */
	public boolean consoleHasLaunch() {
		activate();
		try{
			new DefaultStyledText();
		}catch(SWTLayerException ex){
			return false;
		}
		return true;
	}
	/**
	 * Returns true when console is terminated.
	 * 
	 * @return
	 */
	public boolean consoleIsTerminated() {
		activate();
		try{
			new DefaultLabel(new WithTextMatcher(containsString("<terminated>")));
		}catch(SWTLayerException ex){
			return false;
		}
		return true;
	}
	
	/**
	 * Switches console to the one with name
	 * <code>text<code> using "Display Selected Console" ToolItem.
	 * 
	 * @param text Name of console to switch to.
	 */
	public void switchConsole(String text){
		switchConsole(new IsEqual<String>(text));
	}
	
	
	/**
	 * Switches console to first one which matches given text matcher, using
	 * "Display Selected Console" ToolItem.
	 * 
	 * @param textMatcher Matcher to match console name.
	 */
	@SuppressWarnings("unchecked")
	public void switchConsole(Matcher<String> textMatcher){
		activate();
		ToolItemMenu menu = new ToolItemMenu(new DefaultToolItem("Display Selected Console"), textMatcher);
		menu.select();
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
