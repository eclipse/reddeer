package org.jboss.reddeer.junit.extension.before.test.impl;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenPreferencePage;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
/**
 * Extension for Extension point org.jboss.reddeer.junit.before.test
 * Disables Maven Repo Index downloading on startup
 * Use this system property to enable/disable it:
 *
 * - reddeer.disable.maven.download.repo.index.on.startup=[true|false] (default=true)
 *  
 * @author vlado pakan
 *
 */
public class DoNotDownloadMavenIndexesExt implements IBeforeTest {
	
	private static final Logger log = Logger.getLogger(DoNotDownloadMavenIndexesExt.class);
	
	private static final boolean DISABLE_MAVEN_DOWNLOAD_REPO_INDEX = System.getProperty("reddeer.disable.maven.download.repo.index.on.startup","true")
			.equalsIgnoreCase("true");
	
	private boolean tryToDisableDownloadingRepoIndexes = true;
	
	/**
	 * See {@link IBeforeTest}
	 */
	@Override
	public void runBeforeTest() {
		if (hasToRun()){
			disableMavenDownloadRepoIndexOnStartup();
		}		
	}

	/**
	 * Disables downloading Maven repo indexes on startup
	 */
	private void disableMavenDownloadRepoIndexOnStartup() {
		IEclipsePreferences preferences = DefaultScope.INSTANCE
				.getNode("org.eclipse.m2e.core");
		if (preferences.get("eclipse.m2.updateIndexes", "true")
				.equalsIgnoreCase("true")) {
			log.debug("Trying to disable dowlading maven repo indexes on startup "
					+ "via Windows > Preferences > Maven");
			try {
				MavenPreferencePage mavenPreferencePage = new MavenPreferencePage();
				mavenPreferencePage.open();
				mavenPreferencePage.setDownloadRepoIndexOnStartup(false);
				mavenPreferencePage.ok();
				tryToDisableDownloadingRepoIndexes = false;
				log.debug("Dowlading maven repo indexes on startup disabled");
			} catch (SWTLayerException swtle) {
				log.warn(
						"Error when trying to disable dowlading maven repo indexes on startup",
						swtle);
				// try to close preferences shell in case it stayes open
				try {
					new DefaultShell("Preferences").close();

				} catch (SWTLayerException swtle1) {
					// do nothing
				}
			}

		}
		else{
			tryToDisableDownloadingRepoIndexes = false;
		}
	}
	/**
	 * See {@link IBeforeTest}
	 */
	@Override
	public boolean hasToRun() {
		return DoNotDownloadMavenIndexesExt.DISABLE_MAVEN_DOWNLOAD_REPO_INDEX 
			&& tryToDisableDownloadingRepoIndexes;
	}

}
