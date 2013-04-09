package org.jboss.reddeer.eclipse.datatools.ui.preference;

import java.util.List;

import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.wizard.DriverDefinitionWizard;
import org.jboss.reddeer.eclipse.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;

/**
 * Preference page for managing driver definitions.
 * 
 * @author apodhrad
 * 
 */
public class DriverDefinitionPreferencePage extends PreferencePage {

	public DriverDefinitionPreferencePage() {
		super("Data Management", "Connectivity", "Driver Definitions");
	}

	public List<DriverDefinition> getDriverDefinitions() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Push 'Add..' button and return a wizard for creating a driver definition.
	 * 
	 * @return a wizard for creating a driver definition
	 */
	public DriverDefinitionWizard addDriverDefinition() {
		new PushButton("Add...").click();
		new WaitUntil(new ShellWithTextIsActive("New Driver Definition"),TimePeriod.NORMAL);
		return new DriverDefinitionWizard();
	}

	public void editDriverDefinition() {
		throw new UnsupportedOperationException();
	}

	public void removeDriverDefinition() {
		throw new UnsupportedOperationException();
	}
}
