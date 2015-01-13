package org.jboss.reddeer.junit.extension.before.test.impl;

import org.eclipse.ui.IViewReference;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.swt.util.Display;
/**
 * Extension for Extension point org.jboss.reddeer.junit.before.test
 * Closes Welcome screen prior test is run
 * Use this system property to enable/disable it:
 * 
 * - reddeer.close.welcome.screen=[true|false] (default=true)
 * 
 * @author vlado pakan
 *
 */
public class CloseWelcomeScreenExt implements IBeforeTest {
	
	private static final Logger log = Logger.getLogger(CloseWelcomeScreenExt.class);
	
	private static final boolean CLOSE_WELCOME_SCREEN = RedDeerProperties.CLOSE_WELCOME_SCREEN.getBooleanSystemValue();
	/**
	 * See {@link IBeforeTest}
	 */
	@Override
	public void runBeforeTest() {
		if (hasToRun()){
			closeWelcomeScreen();
		}
	}
	/**
	 * Closes welcome screen
	 */
	private void closeWelcomeScreen() {
		log.debug("Trying to close Welcome Screen");
		for (IViewReference viewReference : WorkbenchLookup.findAllViews()) {
			if (viewReference.getPartName().equals("Welcome")) {
				final IViewReference iViewReference = viewReference;
				Display.syncExec(new Runnable() {
					@Override
					public void run() {
						iViewReference.getPage().hideView(iViewReference);
					}
				});
				log.debug("Welcome Screen closed");
				break;
			}
		}
	}
	/**
	 * See {@link IBeforeTest}
	 */
	@Override
	public boolean hasToRun() {
		return CloseWelcomeScreenExt.CLOSE_WELCOME_SCREEN;
	}

}
