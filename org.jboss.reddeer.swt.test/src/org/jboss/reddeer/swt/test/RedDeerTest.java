package org.jboss.reddeer.swt.test;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.jboss.reddeer.swt.util.Bot;
import org.junit.After;
import org.junit.Before;
/**
 * Parent test for each test of Red Deer
 * @author Vlado Pakan
 *
 */
public class RedDeerTest {
	@Before
	public void setUpRDT(){
		setUp();
	}
	@After
	public void tearDownRDT(){
		tearDown();
	}
  // Default setup for each test   
	protected void setUp(){
	  // close Welcome screen
	  SWTBotView activeView = Bot.get().activeView();
	  if (activeView != null && activeView.getTitle().equals("Welcome")){
	    activeView.close();  
	  }		
	}
  //  Default tearDown for each test
	protected void tearDown(){
		// empty for now can be overridden by child classes
	}
}
