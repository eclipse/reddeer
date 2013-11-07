package org.jboss.reddeer.eclipse.ui.views.log;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.wait.WaitUntil;


/**
 * Represents Log message in Error log
 * @author rawagner
 *
 */
public class LogMessage {
	
	private TreeItem treeItem;
	private int severity;
	
	public LogMessage(TreeItem treeItem, int severity){
		this.treeItem = treeItem;
		this.severity = severity;
	}
	
	public int getSeverity() {
		return severity;
	}
	
	public String getMessage() {
		return treeItem.getCell(0);
	}

	public String getPlugin() {
		return treeItem.getCell(1);
	}


	public String getDate() {
		return treeItem.getCell(2);
	}
	
	public String getStackTrace(){
		treeItem.select();
		new ContextMenu("Event Details").select();
		new WaitUntil(new ShellWithTextIsActive("Event Details"));
		String stackTrace = new LabeledText("Exception Stack Trace:").getText();
		new PushButton("OK").click();
		return stackTrace;
	}
	
	public String getSessionData(){
		treeItem.select();
		new ContextMenu("Event Details").select();
		new WaitUntil(new ShellWithTextIsActive("Event Details"));
		String sessionData = new LabeledText("Session Data:").getText();
		new PushButton("OK").click();
		return sessionData;
	}
	
	public List<LogMessage> getSubLogMessages(){
		List<LogMessage> lm = new ArrayList<LogMessage>();
		for(TreeItem i: treeItem.getItems()){
			LogMessage l = new LogMessage(i, severity);
			lm.add(l);
		}
		return lm;
	}

}
