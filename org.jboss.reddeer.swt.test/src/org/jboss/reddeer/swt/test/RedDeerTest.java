package org.jboss.reddeer.swt.test;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.util.Bot;
import org.junit.After;
import org.junit.Before;
/**
 * Parent test for each test of Red Deer
 * @author Vlado Pakan
 * @author Jiri Peterka
 */
public class RedDeerTest {
	
	@Before
	public void setUpRDT(){
		configureLogging();
		setUp();
	}
	
	private void configureLogging() {
		ConsoleAppender console = new ConsoleAppender();
		String PATTERN = "%-5p [%t][%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		// if you want to enable just add vm argument -Dlog.debug=true
		if (System.getProperty("log.debug").equalsIgnoreCase("true")) {
			console.setThreshold(Level.DEBUG);
		}
		else {
			console.setThreshold(Level.INFO);
		}
		console.activateOptions();
		Logger.getRootLogger().addAppender(console);
	}

	@After
	public void tearDownRDT(){
		tearDown();
	}
  // Default setup for each test   
	protected void setUp(){
	  // close Welcome screen
	  try {
		  SWTBotView activeView = Bot.get().activeView();
		  if (activeView != null && activeView.getTitle().equals("Welcome")){
			    activeView.close();  
			  }
	  } catch (WidgetNotFoundException exc) {
		  // welcome screen not found, no need to close it
	  }
	  		
	}
  //  Default tearDown for each test
	protected void tearDown(){
		// empty for now can be overridden by child classes
	}
}
