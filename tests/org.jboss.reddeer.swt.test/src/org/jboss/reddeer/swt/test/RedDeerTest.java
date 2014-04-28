package org.jboss.reddeer.swt.test;

import org.eclipse.ui.IViewReference;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.junit.watcher.RedDeerWatchdog;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
/**
 * Parent test for each test of Red Deer
 * @deprecated use {@link org.jboss.reddeer.junit.extensionpoint} extensions
 * to set up and clean your tests. Once these extensions are installed they
 * are called automatically when test is running with {@link RedDeerSuite}
 * @author Vlado Pakan
 * @author Jiri Peterka
 * @author jjankovi
 *
 */
@RunWith(RedDeerSuite.class)
@Ignore
public class RedDeerTest {

	@Rule 
    public RedDeerWatchdog rule = new RedDeerWatchdog();
	
	Logger log = Logger.getLogger(RedDeerTest.class);
	
	@Before
	public void setUpRDT(){
		setUp();
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
