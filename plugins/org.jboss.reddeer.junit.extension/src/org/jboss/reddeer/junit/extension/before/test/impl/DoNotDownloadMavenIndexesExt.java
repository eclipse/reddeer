package org.jboss.reddeer.junit.extension.before.test.impl;

import org.eclipse.core.runtime.Platform;
import org.jboss.reddeer.common.exception.RedDeerException;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenPreferencePage;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/** 
 * Extension for Extension point org.jboss.reddeer.junit.before.test. Disables
 * Maven Repo Index downloading on startup Use this system property to
 * enable/disable it:
 *
 * - rd.disableMavenIndex=[true|false]
 * (default=true)
 * 
 * @author vlado pakan
 *
 */
public class DoNotDownloadMavenIndexesExt implements IBeforeTest {

	private static final Logger log = Logger
			.getLogger(DoNotDownloadMavenIndexesExt.class);

	private static final boolean DISABLE_MAVEN_DOWNLOAD_REPO_INDEX = RedDeerProperties.DISABLE_MAVEN_REPOSITORY_DOWNLOAD.getBooleanValue();

	@Override
	public void runBeforeTestClass(String config, TestClass testClass) {
		disableMavenDownloadRepoIndexOnStartup();		
	}

	/** 
	 * See {@link IBeforeTest}.
	 */
	@Override
	public void runBeforeTest(String config, Object target, FrameworkMethod method) {
		// do not run before each test
	}

	/** 
	 * Disables downloading Maven repo indexes on startup.
	 */
	private void disableMavenDownloadRepoIndexOnStartup() {
		String updateIndexesPreferenceString = Platform
				.getPreferencesService()
				.getString("org.eclipse.m2e.core", "eclipse.m2.updateIndexes",
						"true", null);

		// Maven is not installed
		if (updateIndexesPreferenceString == null) {
			return;
		}

		// It is already disabled
		if (updateIndexesPreferenceString.equalsIgnoreCase("false")){
			return;
		}

		log.debug("Trying to disable downloading maven repo indexes on startup "
				+ "via Windows > Preferences > Maven");

		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		MavenPreferencePage mavenPreferencePage = new MavenPreferencePage();

		preferencesDialog.open();
		try {
			preferencesDialog.select(mavenPreferencePage);
		} catch (RedDeerException e){
			// Maven is not installed
			preferencesDialog.cancel();
			return;
		}
		mavenPreferencePage.setDownloadRepoIndexOnStartup(false);
		preferencesDialog.ok();
		
		log.debug("Downloading maven repo indexes on startup disabled");
	}

	/** 
	 * See {@link IBeforeTest}.
	 * @return boolean
	 */
	@Override
	public boolean hasToRun() {
		return DoNotDownloadMavenIndexesExt.DISABLE_MAVEN_DOWNLOAD_REPO_INDEX;
	}
}
