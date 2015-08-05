package org.jboss.reddeer.eclipse.debug.ui.launchConfigurations;

/**
 * Represents Debug configurations dialog 
 * 
 * @author Lucia Jelinkova
 *
 */
public class DebugConfigurationDialog extends LaunchConfigurationDialog {

	public static final String DIALOG_TITLE = "Debug Configurations";
	
	@Override
	public String getTitle() {
		return DIALOG_TITLE;
	}
	
	@Override
	protected String getMenuItemName() {
		return "Debug Configurations...";
	}
}
