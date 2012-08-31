package org.jboss.reddeer.eclipse.ui.views.log;


import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellIsActive;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.eclipse.swt.widgets.TreeItem;

public class LogMessage {
	
	private SWTBotTreeItem treeItem;
	private int severity;
	
	public LogMessage(TreeItem treeItem, int severity){
		this.treeItem = new SWTBotTreeItem(treeItem);
		this.severity = severity;
	}
	
	public int getSeverity() {
		return severity;
	}
	
	public String getMessage() {
		return treeItem.cell(0);
	}

	public String getPlugin() {
		return treeItem.cell(1);
	}


	public String getDate() {
		return treeItem.cell(2);
	}
	
	public String getStackTrace(){
		treeItem.contextMenu("Event Details").click();
		SWTBot bot = new SWTBot();
		bot.waitUntil(shellIsActive("Event Details"));
		String stackTrace = bot.textWithLabel("Exception Stack Trace:").getText();
		bot.button("OK").click();
		return stackTrace;
	}
	
	public String getSessionData(){
		treeItem.contextMenu("Event Details").click();
		SWTBot bot = new SWTBot();
		bot.waitUntil(shellIsActive("Event Details"));
		String sessionData = bot.textWithLabel("Session Data:").getText();
		bot.button("OK").click();
		return sessionData;
	}
	

}
