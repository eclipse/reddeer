package org.jboss.reddeer.eclipse.test.ui.views.log;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.List;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jboss.reddeer.eclipse.test.Activator;
import org.jboss.reddeer.eclipse.ui.views.log.LogMessage;
import org.jboss.reddeer.eclipse.ui.views.log.LogView;
import org.junit.BeforeClass;
import org.junit.Test;

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
	
	@BeforeClass
	public static void setup(){
		ILog log = Activator.getDefault().getLog();
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
		assertTrue(messages.get(1).getSeverity() == IStatus.OK);
		assertTrue(messages.get(1).getPlugin().equals(OK_ID_1));
		assertTrue(messages.get(1).getMessage().equals(OK_MESSAGE_1));
		assertTrue(messages.get(1).getStackTrace().equals("An exception stack trace is not available."));
		
		//test OK Message no.2
		assertTrue(messages.get(0).getSeverity() == IStatus.OK);
		assertTrue(messages.get(0).getPlugin().equals(OK_ID_2));
		assertTrue(messages.get(0).getMessage().equals(OK_MESSAGE_2));
		assertTrue(messages.get(0).getStackTrace().contains(OK_STACK_2));
	}
	
	@Test
	public void getInfoMessage(){
		logView = new LogView();
		logView.open();
		List<LogMessage> messages = logView.getInfoMessages();
		assertTrue("No INFO messages found!", !messages.isEmpty());
		
		//test INFO Message no.1
		assertTrue(messages.get(1).getSeverity() == IStatus.INFO);
		assertTrue(messages.get(1).getPlugin().equals(INFO_ID_1));
		assertTrue(messages.get(1).getMessage().equals(INFO_MESSAGE_1));
		assertTrue(messages.get(1).getStackTrace().equals("An exception stack trace is not available."));
				
		//test INFO Message no.2
		assertTrue(messages.get(0).getSeverity() == IStatus.INFO);
		assertTrue(messages.get(0).getPlugin().equals(INFO_ID_2));
		assertTrue(messages.get(0).getMessage().equals(INFO_MESSAGE_2));
		assertTrue(messages.get(0).getStackTrace().contains(INFO_STACK_2));
	}
	
	@Test
	public void getWarningMessage(){
		logView = new LogView();
		logView.open();
		List<LogMessage> messages = logView.getWarningMessages();
		assertTrue("No WARNING messages found!", !messages.isEmpty());
		
		//test WARNING Message no.1
		assertTrue(messages.get(1).getSeverity() == IStatus.WARNING);
		assertTrue(messages.get(1).getPlugin().equals(WARNING_ID_1));
		assertTrue(messages.get(1).getMessage().equals(WARNING_MESSAGE_1));
		assertTrue(messages.get(1).getStackTrace().contains(WARNING_STACK_1));
				
		//test WARNING Message no.2
		assertTrue(messages.get(0).getSeverity() == IStatus.WARNING);
		assertTrue(messages.get(0).getPlugin().equals(WARNING_ID_2));
		assertTrue(messages.get(0).getMessage().equals(WARNING_MESSAGE_2));
		assertTrue(messages.get(0).getStackTrace().contains(WARNING_STACK_2));
	}
	
	@Test
	public void getErrorMessage(){
		logView = new LogView();
		logView.open();
		List<LogMessage> messages = logView.getErrorMessages();
		assertTrue("No ERROR messages found!", !messages.isEmpty());
		
		//test ERROR Message no.1
		assertTrue(messages.get(1).getSeverity() == IStatus.ERROR);
		assertTrue(messages.get(1).getPlugin().equals(ERROR_ID_1));
		assertTrue(messages.get(1).getMessage().equals(ERROR_MESSAGE_1));
		assertTrue(messages.get(1).getStackTrace().contains(ERROR_STACK_1));
						
		//test ERROR Message no.2
		assertTrue(messages.get(0).getSeverity() == IStatus.ERROR);
		assertTrue(messages.get(0).getPlugin().equals(ERROR_ID_2));
		assertTrue(messages.get(0).getMessage().equals(ERROR_MESSAGE_2));
		assertTrue(messages.get(0).getStackTrace().contains(ERROR_STACK_2));
	}
	
}
