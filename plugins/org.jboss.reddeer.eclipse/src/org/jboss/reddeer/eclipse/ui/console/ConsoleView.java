/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.ui.console;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.WidgetIsFound;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.eclipse.condition.ConsoleHasLabel;
import org.jboss.reddeer.eclipse.condition.ConsoleHasLaunch;
import org.jboss.reddeer.eclipse.condition.ConsoleIsTerminated;
import org.jboss.reddeer.swt.impl.menu.ToolItemMenu;
import org.jboss.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Represents Console view in Eclipse
 * 
 * @author jjankovi, mlabuda@redhat.com
 *
 */
public class ConsoleView extends WorkbenchView {

	private static final String TERMINATE = "Terminate";
	private static final String CLEAR_CONSOLE = "Clear Console";
	
	/**
	 * Constructs the view with "Console".
	 */
	public ConsoleView() {
		super("Console");
	}
	
	/**
	 * Gets text from console. 
	 * 
	 * @return console text, if there is a console, null otherwise
	 */
	public String getConsoleText() {
		activate();
		WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.custom.StyledText.class);
		new WaitUntil(widgetIsFound, TimePeriod.NORMAL, false);
		// Check whether there is a console to display or not
		if (widgetIsFound.getWidget() == null) {
			return null;
		}
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
		new DefaultToolItem(CLEAR_CONSOLE).click();
		new WaitUntil(new ConsoleHasText(""));
		log.info("Console cleared");
	}
	
	/**
	 * Finds out whether console can be cleared or not.
	 * 
	 * @return true if console can be cleared (there is a tool item Clear Console and is enabled), false otherwise
	 */
	public boolean canClearConsole() {
		return toolItemExistsAndIsEnabled(CLEAR_CONSOLE);
	}
	
	/**
	 * Removes a launch from the console.
	 */
	public void removeLaunch() {
		log.info("Removing launch from console");
		activate();
		new DefaultToolItem("Remove Launch").click();
		log.info("Launch removed");
	}
	
	/**
	 * Removes all terminated launches.
	 */
	public void removeAllTerminatedLaunches() {
		log.info("Removing terminated launches from console");
		activate();
		new DefaultToolItem("Remove All Terminated Launches").click();
		new WaitWhile(new ConsoleHasLaunch());
		log.info("Terminated launches cleared");
	}
	
	/**
	 * Terminates an active console via the tool item <i>Terminate</i>. 
	 */
	public void terminateConsole() {
		log.info("Terminating console");
		activate();
		DefaultToolItem terminate = new DefaultToolItem(TERMINATE);
		if (terminate.isEnabled()) {
			terminate.click();
			new WaitUntil(new ConsoleIsTerminated());
			log.info("Console terminated");
		} else {
			log.info("Console was terminated earlier");
		}
	}
	
	/**
	 * Finds out whether a console can be terminated or not
	 * 
	 * @return true if console has terminate tool item and it is enabled, false otherwise
	 */
	public boolean canTerminateConsole() {
		return toolItemExistsAndIsEnabled(TERMINATE);
	}
	
	/**
	 * Finds out whether a tool item with specified text exists and is enabled or not.
	 * 
	 * @param toolItemText
	 * @return if tool item exists and is enabled, false otherwise
	 */
	private boolean toolItemExistsAndIsEnabled(String toolItemText) {
		activate();
		WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.widgets.ToolItem.class,
				new WithTextMatcher(toolItemText));
		widgetIsFound.test();
		org.eclipse.swt.widgets.Widget widget = widgetIsFound.getWidget();
		if (widget == null) {
			return false;
		}
		return new DefaultToolItem((org.eclipse.swt.widgets.ToolItem) widget).isEnabled();
	}
	
	/**
	 * Toggles the button indicating if the view should be activated on standard output change.
	 *
	 * @param toggle the toggle
	 */
	public void toggleShowConsoleOnStandardOutChange(boolean toggle){
		activate();
		new DefaultToolItem("Show Console When Standard Out Changes").toggle(toggle);
	}
	
	/**
	 * Returns true if console has launch.
	 *
	 * @return true, if successful
	 */
	public boolean consoleHasLaunch() {
		activate();
		return new WidgetIsFound(org.eclipse.swt.custom.StyledText.class).test();
	}
	
	/**
	 * Returns true when console is terminated.
	 *
	 * @return true, if successful
	 */
	public boolean consoleIsTerminated() {
		String consoleLabel = getConsoleLabel();
		return consoleLabel != null && consoleLabel.contains("<terminated>");
	}
	
	/**
	 * Switches console to the one with specified name
	 * using "Display Selected Console" ToolItem.
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
		new WaitUntil(new ConsoleHasLabel(textMatcher));
	}
	
	/**
	 * 
	 * This is not exactly a condition for checking if the console contains text.
	 * For this purpose use org.jboss.reddeer.eclipse.condition.ConsoleHasText
	 *
	 */
	private class ConsoleHasText extends AbstractWaitCondition {
		private String consoleText;

		public ConsoleHasText(String consoleText) {
			this.consoleText = consoleText;
		}

		@Override
		public boolean test() {
			WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.custom.StyledText.class);
			widgetIsFound.test();
			org.eclipse.swt.widgets.Widget swtWidget = widgetIsFound.getWidget();
			return (swtWidget == null) ? false : consoleText.equals(
					WidgetHandler.getInstance().getText(swtWidget));
		}

		@Override
		public String description() {
			return "console text is \"" + this.consoleText + "\"";
		}

	}
	
	/**
	 * Returns console label title or null when console has no label.
	 *
	 * @return the console label
	 */
	public String getConsoleLabel (){
		activate();
		WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.widgets.Label.class);
		widgetIsFound.test();
		org.eclipse.swt.widgets.Widget swtWidget = widgetIsFound.getWidget();
		return (swtWidget == null) ? null : WidgetHandler.getInstance().getText(swtWidget);
	}
}
