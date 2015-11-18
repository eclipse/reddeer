package org.jboss.reddeer.eclipse.debug.ui.launchConfigurations;

/**
 * Represents Run configurations dialog 
 * 
 * @author Lucia Jelinkova
 *
 */
public class RunConfigurationDialog extends LaunchConfigurationDialog {

	public static final String DIALOG_TITLE = "Run Configurations";
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationDialog#getTitle()
	 */
	@Override
	public String getTitle() {
		return DIALOG_TITLE;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.debug.ui.launchConfigurations.LaunchConfigurationDialog#getMenuItemName()
	 */
	@Override
	protected String getMenuItemName() {
		return "Run Configurations...";
	}
}
