package org.jboss.reddeer.junit.extension.before.test.impl;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.equinox.p2.ui.sdk.scheduler.AutomaticUpdatesPreferencePage;
import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.junit.extensionpoint.IBeforeTest;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

public class DoNotCheckForUpdatesExt implements IBeforeTest {

	private static final Logger log = Logger
			.getLogger(DoNotCheckForUpdatesExt.class);

	private static final boolean DISABLE_CHECK_FOR_UPDATES = System
			.getProperty("reddeer.disable.check.for.updates", "true")
			.equalsIgnoreCase("true");

	private boolean tryToDisableAutomaticUpdates = true;

	@Override
	public void runBeforeTest() {
		if (hasToRun()) {
			log.debug("Disabling check for updates");
			try {
				WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
				AutomaticUpdatesPreferencePage automaticUpdatesPreferencePage = new AutomaticUpdatesPreferencePage();

				preferencesDialog.open();
				preferencesDialog.select(automaticUpdatesPreferencePage);

				automaticUpdatesPreferencePage.toggleAutomaticUpdates(false);
				preferencesDialog.ok();
				tryToDisableAutomaticUpdates = false;
				log.debug("Automatic updates are disabled");
			} catch (SWTLayerException swtle) {
				log.warn("Error when trying to disable automatic updates",
						swtle);
				// try to close preferences shell in case it stayes open
				try {
					new DefaultShell("Preferences").close();

				} catch (SWTLayerException swtle1) {
					// do nothing
				}
			}
		}
		// IPreferenceStore pref =
		// AutomaticUpdatePlugin.getDefault().getPreferenceStore();
		// pref.setValue(PreferenceConstants.PREF_AUTO_UPDATE_ENABLED, false);

	}

	@Override
	public boolean hasToRun() {
		return DoNotCheckForUpdatesExt.DISABLE_CHECK_FOR_UPDATES
				&& tryToDisableAutomaticUpdates;
	}

}
