package org.jboss.reddeer.eclipse.ui.project;

import java.util.List;

import org.jboss.reddeer.eclipse.ui.wizards.WizardPageSettings;
import org.osgi.framework.Version;

public class ProjectSettingValidator {

	private final String pluginId;
	private final String pluginName;
	private final String pluginVersion;
	private final String pluginProvider;
	private final WizardPageSettings wizardPage;
	private final List<String> projectNames;

	public ProjectSettingValidator(String pluginId, String pluginName,
			String pluginVersion, String pluginProvider, List<String> projectNames,
			WizardPageSettings wizardPage) {
		this.pluginId = pluginId;
		this.pluginName = pluginName;
		this.pluginVersion = pluginVersion;
		this.pluginProvider = pluginProvider;
		this.projectNames = projectNames;
		this.wizardPage = wizardPage;
	}

	public void validate() {

		wizardPage.setPageComplete(true);
		wizardPage.setErrorMessage(null);

		if (pluginName.trim().length() == 0) {
			wizardPage.setErrorMessage("Plugin name cannot be empty!");
			wizardPage.setPageComplete(false);
			return;
		}

		if (projectNames.contains(pluginName)) {
			wizardPage
					.setErrorMessage("A project by that name already exists!");
			wizardPage.setPageComplete(false);
			return;
		}

		if (pluginId.trim().length() == 0) {
			wizardPage.setErrorMessage("Please specify the plugin id.");
			wizardPage.setPageComplete(false);
			return;
		}

		if (!isValidCompositeID3_0(pluginId)) {
			wizardPage
					.setErrorMessage("Invalid plugin id! Legal characters are A-Z a-z 0-9 . _ -");
			wizardPage.setPageComplete(false);
			return;
		}
		
		if (pluginProvider.trim().length() == 0) {
			wizardPage.setErrorMessage("Please specify plugin provider company.");
			wizardPage.setPageComplete(false);
			return;
		}

		try {
			new Version(pluginVersion);
		} catch (IllegalArgumentException ex) {
			wizardPage
					.setErrorMessage("The specified version does not have the correct format (major.minor.micro.qualifier) or contains invalid characters!");
			wizardPage.setPageComplete(false);
			return;
		}
	}

	// copied from IdUtil from PDE.
	private boolean isValidCompositeID3_0(String name) {
		if (name.length() <= 0) {
			return false;
		}
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if ((c < 'A' || 'Z' < c) && (c < 'a' || 'z' < c)
					&& (c < '0' || '9' < c) && c != '_' && c != '-') {
				if (i == 0 || i == name.length() - 1 || c != '.') {
					return false;
				}
			}
		}
		return true;
	}

}