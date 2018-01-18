/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.console;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.handler.LabelHandler;
import org.eclipse.reddeer.core.handler.StyledTextHandler;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasLabel;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasLaunch;
import org.eclipse.reddeer.eclipse.condition.ConsoleIsTerminated;
import org.eclipse.reddeer.swt.impl.menu.ToolItemMenuItem;
import org.eclipse.reddeer.swt.impl.styledtext.DefaultStyledText;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;
import org.eclipse.swt.widgets.Control;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;

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
		WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.custom.StyledText.class, cTabItem.getControl());
		new WaitUntil(widgetIsFound, TimePeriod.DEFAULT, false);
		// Check whether there is a console to display or not
		if (widgetIsFound.getResult() == null) {
			return null;
		}
		// wait for text to appear
		new WaitWhile(new ConsoleHasText(""),TimePeriod.SHORT,false);
		return new DefaultStyledText(cTabItem).getText();
	}
	
	/**
	 * Clears the console.
	 */
	public void clearConsole() {
		log.info("Clearing console");
		activate();		
		new DefaultToolItem(cTabItem.getFolder(), CLEAR_CONSOLE).click();
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
		new DefaultToolItem(cTabItem.getFolder(), "Remove Launch").click();
		log.info("Launch removed");
	}
	
	/**
	 * Removes all terminated launches.
	 */
	public void removeAllTerminatedLaunches() {
		log.info("Removing terminated launches from console");
		activate();
		new DefaultToolItem(cTabItem.getFolder(), "Remove All Terminated Launches").click();
		new WaitWhile(new ConsoleHasLaunch());
		log.info("Terminated launches cleared");
	}
	
	/**
	 * Terminates an active console via the tool item <i>Terminate</i>. 
	 */
	public void terminateConsole() {
		log.info("Terminating console");
		activate();
		DefaultToolItem terminate = new DefaultToolItem(cTabItem.getFolder(), TERMINATE);
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
		WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.widgets.ToolItem.class, cTabItem.getFolder().getControl(), 
				new WithTextMatcher(toolItemText));
		widgetIsFound.test();
		org.eclipse.swt.widgets.Widget widget = widgetIsFound.getResult();
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
		new DefaultToolItem(cTabItem.getFolder(), "Show Console When Standard Out Changes").toggle(toggle);
	}
	
	/**
	 * Returns true if console has launch.
	 *
	 * @return true, if successful
	 */
	public boolean consoleHasLaunch() {
		activate();
		return new WidgetIsFound(org.eclipse.swt.custom.StyledText.class, cTabItem.getControl()).test();
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
		ToolItemMenuItem menu = new ToolItemMenuItem(new DefaultToolItem(cTabItem.getFolder(), "Display Selected Console"), textMatcher);
		menu.select();
		new WaitUntil(new ConsoleHasLabel(textMatcher));
	}
	
	/**
	 * 
	 * This is not exactly a condition for checking if the console contains text.
	 * For this purpose use org.eclipse.reddeer.eclipse.condition.ConsoleHasText
	 *
	 */
	private class ConsoleHasText extends AbstractWaitCondition {
		private String consoleText;

		public ConsoleHasText(String consoleText) {
			this.consoleText = consoleText;
		}

		@Override
		public boolean test() {
			WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.custom.StyledText.class, cTabItem.getControl());
			widgetIsFound.test();
			org.eclipse.swt.widgets.Widget swtWidget = widgetIsFound.getResult();
			return (swtWidget == null) ? false : consoleText.equals(
					StyledTextHandler.getInstance().getText((org.eclipse.swt.custom.StyledText)swtWidget));
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
	public String getConsoleLabel(){
		activate();
		WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.widgets.Label.class, cTabItem.getControl());
		widgetIsFound.test();
		org.eclipse.swt.widgets.Widget swtWidget = widgetIsFound.getResult();
		return (swtWidget == null) ? null : LabelHandler.getInstance().getText((org.eclipse.swt.widgets.Label)swtWidget);
	}

	/**
	 * Returns a control registered via adapters. This is usually StyledText or
	 * Canvas.
	 * 
	 * @return registered control
	 */
	protected Control getRegisteredControl() {
		activate();
		WidgetIsFound widgetIsFound = new WidgetIsFound(org.eclipse.swt.custom.StyledText.class, cTabItem.getControl());
		new WaitUntil(widgetIsFound, TimePeriod.SHORT, false);
		// Check whether there is a console to display or not
		if (widgetIsFound.getResult() == null) {
			log.debug("There is no console in console view.");
			return null;
		}
		return (org.eclipse.swt.custom.StyledText) widgetIsFound.getResult();
	}

}
