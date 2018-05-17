/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.ui.views.log;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.text.LabeledText;


/**
 * Represents Log message in Error log
 * @author rawagner
 *
 */
public class LogMessage {
	
	private TreeItem treeItem;
	private int severity;
	
	/**
	 * Instantiates a new log message.
	 *
	 * @param treeItem the tree item
	 * @param severity the severity
	 */
	public LogMessage(TreeItem treeItem, int severity){
		this.treeItem = treeItem;
		this.severity = severity;
	}
	
	/**
	 * Returns a severity.
	 * 
	 * @return severity
	 */
	public int getSeverity() {
		return severity;
	}
	
	/**
	 * Returns a message.
	 * 
	 * @return Message
	 */
	public String getMessage() {
		return treeItem.getCell(0);
	}

	/**
	 * Returns a plugin.
	 * 
	 * @return Plugin
	 */
	public String getPlugin() {
		return treeItem.getCell(1);
	}

	/**
	 * Returns the date.
	 * 
	 * @return Date
	 */
	public String getDate() {
		return treeItem.getCell(2);
	}
	
	/**
	 * Returns a stack trace.
	 * 
	 * @return Stack trace
	 */
	public String getStackTrace(){
		treeItem.select();
		new ContextMenuItem("Event Details").select();
		new WaitUntil(new ShellIsAvailable("Event Details"));
		String stackTrace = new LabeledText("Exception Stack Trace:").getText();
		new PushButton("OK").click();
		return stackTrace;
	}
	
	/**
	 * Returns session data.
	 * 
	 * @return Session data
	 */
	public String getSessionData(){
		treeItem.select();
		new ContextMenuItem("Event Details").select();
		new WaitUntil(new ShellIsAvailable("Event Details"));
		String sessionData = new LabeledText("Session Data:").getText();
		new PushButton("OK").click();
		return sessionData;
	}
	
	/**
	 * Returns sublog messages.
	 * 
	 * @return Sublog messages
	 */
	public List<LogMessage> getSubLogMessages(){
		List<LogMessage> lm = new ArrayList<LogMessage>();
		for(TreeItem i: treeItem.getItems()){
			LogMessage l = new LogMessage(i, severity);
			lm.add(l);
		}
		return lm;
	}

	@Override
	public String toString() {
		return "LogMessage [getSeverity()=" + getSeverity() + ", getMessage()=" + getMessage() + ", getPlugin()="
				+ getPlugin() + ", getDate()=" + getDate() + ", getStackTrace()=" + getStackTrace()
				+ ", getSessionData()=" + getSessionData() + ", getSubLogMessages()=" + getSubLogMessages() + "]";
	}
	
	
}
