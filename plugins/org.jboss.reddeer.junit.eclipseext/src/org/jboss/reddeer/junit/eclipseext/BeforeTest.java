package org.jboss.reddeer.junit.eclipseext;

import org.eclipse.ui.IViewReference;
import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenPreferencePage;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.lookup.WorkbenchLookup;
import org.jboss.reddeer.swt.util.Display;
/**
 * Proceeds default actions for all RedDeer Eclipse tests prior first test is run
 * These system properties can be used to change default behavior:
 * 
 * - reddeer.disable.maven.download.repo.index.on.startup=[true|false] (default=true)
 * - reddeer.close.welcome.screen=[true|false] (default=true)
 * 
 * @author vlado pakan
 *
 */
public class BeforeTest implements IBeforeTest {
	
	private static final Logger log = Logger.getLogger(BeforeTest.class);
	/**
	 * See {@link IBeforeTest}
	 */
	@Override
	public void runBeforeTest() {
		String closeWelcomeScreen = 
			System.getProperty("reddeer.close.welcome.screen","true");
		if (closeWelcomeScreen.equalsIgnoreCase("true")){
			closeWelcomeScreen();
		}
		
		String disableMavenDownloadRepoIndexOnStartup =
			System.getProperty("reddeer.disable.maven.download.repo.index.on.startup","true");
		if (disableMavenDownloadRepoIndexOnStartup.equalsIgnoreCase("true")){
			disableMavenDownloadRepoIndexOnStartup();
		}		
	}
	/**
	 * Disables downloading Maven repo indexes on startup
	 */
	private void disableMavenDownloadRepoIndexOnStartup() {
		try{
			log.debug("Trying to disable dowlading maven repo indexes on startup "
				+ "via Windows > Preferences > Maven");
			MavenPreferencePage mavenPreferencePage = new MavenPreferencePage();
			mavenPreferencePage.open();
			mavenPreferencePage.disableDownloadRepoIndexOnStartup();
			mavenPreferencePage.ok();
		} catch (SWTLayerException swtle){
			log.warn("Error when trying to disable dowlading maven repo indexes on startup",swtle);
			// try to close preferences shell in case it stayes open
			try{
				new DefaultShell("Preferences").close();
				
			} catch (SWTLayerException swtle1){
				// do nothing
			}
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

}
