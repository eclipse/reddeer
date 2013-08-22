package org.jboss.reddeer.swt.test;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.ui.IViewReference;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Before;
/**
 * Parent test for each test of Red Deer
 * @author Vlado Pakan
 * @author Jiri Peterka
 * @author jjankovi
 *
 */
public class RedDeerTest {

	Logger log = Logger.getLogger(RedDeerTest.class);
	
	@Before
	public void setUpRDT(){
		configureLogging();
		setUp();
	}
	
	private void configureLogging() {
		if (Logger.getRootLogger().getAppender("console") != null)  return;
			
		ConsoleAppender console = new ConsoleAppender();
		console.setName("console");
		String PATTERN = "%-5p [%t][%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		// if you want to enable just add vm argument -Dlog.debug=true
		String debugProp = System.getProperty("log.debug");
		if (debugProp != null && debugProp.equalsIgnoreCase("true")) {
			console.setThreshold(Level.DEBUG);
		} else {
			console.setThreshold(Level.INFO);
		}
		console.activateOptions();
		Logger.getRootLogger().addAppender(console);
		log.info("Logging threshold set to " + console.getThreshold());
	}

	@After
	public void tearDownRDT(){
		tearDown();
	}
  // Default setup for each test   
	protected void setUp() {
	  // close Welcome screen
		for (IViewReference viewReference : WorkbenchLookup.findAllViews()) {
			if (viewReference.getPartName().equals("Welcome")) {
				final IViewReference iViewReference = viewReference;
				Display.syncExec(new Runnable() {
					@Override
					public void run() {
						iViewReference.getPage().hideView(iViewReference);
					}
				});
				break;
			}
		}
	}	

  //  Default tearDown for each test
	protected void tearDown(){
		// empty for now can be overridden by child classes
	}
}
