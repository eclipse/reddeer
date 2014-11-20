package org.jboss.reddeer.eclipse.equinox.p2.ui.sdk.scheduler;

import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.workbench.preference.WorkbenchPreferencePage;

/**
 * This page represents Automatic Updates preference page
 * @author rawagner
 *
 */
public class AutomaticUpdatesPreferencePage extends WorkbenchPreferencePage{
	
	/**
	 * Default constructor
	 */
	public AutomaticUpdatesPreferencePage() {
		super("Install/Update","Automatic Updates");
	}
	
	/**
	 * Toggle automatic updates
	 * @param toggle check or uncheck automatic updates check box
	 */
	public void toggleAutomaticUpdates(boolean toggle){
		new CheckBox("Automatically find new updates and notify me").toggle(toggle);
	}
	
	/**
	 * Check if automatic updates are enabled
	 * @return true if automatic updates are enabled, false otherwise
	 */
	public boolean isAutomaticallyUpdates(){
		return new CheckBox("Automatically find new updates and notify me").isChecked();
	}

}
