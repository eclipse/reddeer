package org.jboss.reddeer.eclipse.ui.views.log;

import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.impl.menu.ToolbarMenu;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

/**
 * Represents Error Log view
 * 
 * @author rawagner
 *
 */
public class LogView extends WorkbenchView{
	
	public static final String OK_SEVERITY="OK";
	public static final String INFORMATION_SEVERITY="Information";
	public static final String WARNING_SEVERITY="Warning";
	public static final String ERROR_SEVERITY="Error";
	
	public LogView(){
		super("Error Log");
	}
	
	/**
	 * 
	 * @return list of messages with severity OK (according to IStatus)
	 */
	
	public List<LogMessage> getOKMessages() {
		setFilter(OK_SEVERITY);
		SWTBotTreeItem[] items = viewObject.bot().tree().getAllItems();
		List<LogMessage> messages = new ArrayList<LogMessage>();
		for(SWTBotTreeItem item : items){
			messages.add(new LogMessage(item.widget, IStatus.OK));
		}
		return messages;
	}
	
	/**
	 * 
	 * @return list of messages with severity INFO (according to IStatus)
	 */
	public List<LogMessage> getInfoMessages() {
		setFilter(INFORMATION_SEVERITY);
		SWTBotTreeItem[] items = viewObject.bot().tree().getAllItems();
		List<LogMessage> messages = new ArrayList<LogMessage>();
		for(SWTBotTreeItem item : items){
			messages.add(new LogMessage(item.widget, IStatus.INFO));
		}
		return messages;
	}
	/**
	 * 
	 * @return list of messages with severity WARNING (according to IStatus)
	 */
	public List<LogMessage> getWarningMessages() {
		setFilter(WARNING_SEVERITY);
		SWTBotTreeItem[] items = viewObject.bot().tree().getAllItems();
		List<LogMessage> messages = new ArrayList<LogMessage>();
		for(SWTBotTreeItem item : items){
			messages.add(new LogMessage(item.widget, IStatus.WARNING));
		}
		return messages;
	}
	/**
	 * 
	 * @return list of messages with severity ERROR (according to IStatus)
	 */
	public List<LogMessage> getErrorMessages() {
		setFilter(ERROR_SEVERITY);
		SWTBotTreeItem[] items = viewObject.bot().tree().getAllItems();
		List<LogMessage> messages = new ArrayList<LogMessage>();
		for(SWTBotTreeItem item : items){
			messages.add(new LogMessage(item.widget, IStatus.ERROR));
		}
		return messages;
	}
	
	private void setFilter(String severity){
		ToolbarMenu tmenu = new ToolbarMenu("Filters...");
		tmenu.select();
		viewObject.bot().waitUntil(shellIsActive("Log Filters"));
		SWTBot filtersBot = viewObject.bot().activeShell().bot();
		filtersBot.checkBoxInGroup(OK_SEVERITY, "Event Types").deselect();
		filtersBot.checkBoxInGroup(INFORMATION_SEVERITY, "Event Types").deselect();
		filtersBot.checkBoxInGroup(WARNING_SEVERITY, "Event Types").deselect();
		filtersBot.checkBoxInGroup(ERROR_SEVERITY, "Event Types").deselect();
		filtersBot.checkBoxInGroup(severity, "Event Types").select();
		
		filtersBot.checkBox("Limit visible events to:").deselect();
		filtersBot.button("OK").click();
	}
	
	
	
}
