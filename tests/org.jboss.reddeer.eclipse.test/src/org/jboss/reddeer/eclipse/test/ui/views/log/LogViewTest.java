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
package org.jboss.reddeer.eclipse.test.ui.views.log;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.eclipse.ui.views.log.LogMessage;
import org.jboss.reddeer.eclipse.ui.views.log.LogView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class LogViewTest {

	public static final String OK_ID_1="fake_ID_OK_1";
	public static final String OK_MESSAGE_1="fake_OK_1";
	
	public static final String OK_ID_2="fake_ID_OK_2";
	public static final String OK_MESSAGE_2="fake_OK_2";
	public static final String OK_STACK_2="fake_OK_2";
	
	public static final String ERROR_ID_1="fake_ID_ERROR_1";
	public static final String ERROR_MESSAGE_1="fake_ERROR_1";
	public static final String ERROR_STACK_1="fake_ERROR_1";
	
	public static final String ERROR_ID_2="fake_ID_ERROR_2";
	public static final String ERROR_MESSAGE_2="fake_ERROR_2";
	public static final String ERROR_STACK_2="fake_ERROR_2";
	
	public static final String WARNING_ID_1="fake_ID_WARNING_1";
	public static final String WARNING_MESSAGE_1="fake_WARNING_1";
	public static final String WARNING_STACK_1="fake_WARNING_1";
	
	public static final String WARNING_ID_2="fake_ID_WARNING_2";
	public static final String WARNING_MESSAGE_2="fake_WARNING_2";
	public static final String WARNING_STACK_2="fake_WARNING_2";
	
	public static final String INFO_ID_1="fake_ID_INFO_1";
	public static final String INFO_MESSAGE_1="fake_INFO_1";
	
	public static final String INFO_ID_2="fake_ID_INFO_2";
	public static final String INFO_MESSAGE_2="fake_INFO_2";
	public static final String INFO_STACK_2="fake_INFO_2";
	
	
	
	
	private LogView logView;
	
	@Before
	public void setup(){
		ILog log= Platform.getLog(Platform.getBundle(Activator.PLUGIN_ID));
		
		log.log(new Status(IStatus.ERROR,ERROR_ID_1,ERROR_MESSAGE_1,new NullPointerException(ERROR_STACK_1)));
		log.log(new Status(IStatus.ERROR,ERROR_ID_2,ERROR_MESSAGE_2,new NullPointerException(ERROR_STACK_2)));
		
		log.log(new Status(IStatus.OK,OK_ID_1,OK_MESSAGE_1,null));
		log.log(new Status(IStatus.OK,OK_ID_2,OK_MESSAGE_2,new NullPointerException(OK_STACK_2)));
		
		log.log(new Status(IStatus.WARNING,WARNING_ID_1,WARNING_MESSAGE_1,new IllegalArgumentException(WARNING_STACK_1)));
		log.log(new Status(IStatus.WARNING,WARNING_ID_2,WARNING_MESSAGE_2,new NullPointerException(WARNING_STACK_2)));
		
		log.log(new Status(IStatus.INFO,INFO_ID_1,INFO_MESSAGE_1,null));
		log.log(new Status(IStatus.INFO,INFO_ID_2,INFO_MESSAGE_2,new NullPointerException(INFO_STACK_2)));
	}
	
	@Test
	public void getOKMessage(){
		logView = new LogView();
		logView.open();
		List<LogMessage> messages = logView.getOKMessages();
		assertTrue("No OK messages found!", !messages.isEmpty());
		
		//test OK Message no.1
		assertTrue(messageIsAvailable(messages, IStatus.OK, OK_ID_1,
				OK_MESSAGE_1, "An exception stack trace is not available."));

		//test OK Message no.2
		assertTrue(messageIsAvailable(messages, IStatus.OK, OK_ID_2,
				OK_MESSAGE_2, OK_STACK_2));		
	}
	
	@Test
	public void getInfoMessage(){
		logView = new LogView();
		logView.open();
		List<LogMessage> messages = logView.getInfoMessages();
		assertTrue("No INFO messages found!", !messages.isEmpty());
		
		//test INFO Message no.1
		assertTrue(messageIsAvailable(messages, IStatus.INFO, INFO_ID_1,
				INFO_MESSAGE_1, "An exception stack trace is not available."));
				
		//test INFO Message no.2
		assertTrue(messageIsAvailable(messages, IStatus.INFO, INFO_ID_2,
				INFO_MESSAGE_2, INFO_STACK_2));
	}
	
	@Test
	public void getWarningMessage(){
		logView = new LogView();
		logView.open();
		List<LogMessage> messages = logView.getWarningMessages();
		assertTrue("No WARNING messages found!", !messages.isEmpty());
		
		//test WARNING Message no.1
		assertTrue(messageIsAvailable(messages, IStatus.WARNING, WARNING_ID_1,
				WARNING_MESSAGE_1, WARNING_STACK_1));
				
		//test WARNING Message no.2
		assertTrue(messageIsAvailable(messages, IStatus.WARNING, WARNING_ID_2,
				WARNING_MESSAGE_2, WARNING_STACK_2));
	}
	
	@Test
	public void getErrorMessage(){
		logView = new LogView();
		logView.open();
		List<LogMessage> messages = logView.getErrorMessages();
		assertTrue("No ERROR messages found!", !messages.isEmpty());
		
		//test ERROR Message no.1
		assertTrue(messageIsAvailable(messages, IStatus.ERROR, ERROR_ID_1,
				ERROR_MESSAGE_1, ERROR_STACK_1));
				
		//test ERROR Message no.2
		assertTrue(messageIsAvailable(messages, IStatus.ERROR, ERROR_ID_2,
				ERROR_MESSAGE_2, ERROR_STACK_2));
	}

	@Test
	public void testClearAndRestore(){ 
		logView = new LogView();
		logView.open();
		assertFalse("There must be messages", logView.getErrorMessages().isEmpty());
		logView.clearLog();				
		assertTrue("There should be messages", logView.getErrorMessages().isEmpty());
		logView.restoreLog();				
		assertFalse("There should be messages", logView.getErrorMessages().isEmpty());			
	}
	
	@Test
	public void testDelete(){ 
		logView = new LogView();
		logView.open();
		assertFalse("There must be messages", logView.getErrorMessages().isEmpty());
		logView.deleteLog();				
		assertTrue("There should be no messages", logView.getErrorMessages().isEmpty());
		logView.deleteLog(); //https://github.com/jboss-reddeer/reddeer/pull/953
		logView.restoreLog();				
		assertTrue("There should be no messages", logView.getErrorMessages().isEmpty());
	}

	
	private boolean messageIsAvailable(List<LogMessage> messages, int severity, String plugin, String message,String stackTrace) {
		for (LogMessage m : messages) {
			if (m.getSeverity() == severity && m.getPlugin().equals(plugin) && m.getMessage().equals(message)) {
				return true;
			}
		}
		
		return false;
	}
	
	@After
	public void cleanup() {
		Platform.getLogFileLocation().toFile().delete();
	}
	
}
