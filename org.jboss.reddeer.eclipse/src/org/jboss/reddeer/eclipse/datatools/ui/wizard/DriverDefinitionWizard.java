package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.impl.button.PushButton;

/**
 * Wizard for creating a new driver definition.
 * 
 * @author apodhrad
 * 
 */
public class DriverDefinitionWizard extends WizardDialog {

	@Override
	public DriverDefinitionPage getFirstPage() {
		return new DriverDefinitionPage(this, 0);
	}

	/**
	 * Create a given driver definition.
	 * 
	 * @param driverDefinition
	 */
	public void create(DriverDefinition driverDefinition) {
		DriverTemplate drvTemp = driverDefinition.getDriverTemplate();
		DriverDefinitionPage page = getFirstPage();
		page.show();
		page.selectDriverTemplate(drvTemp.getType(), drvTemp.getVersion());
		page.setName(driverDefinition.getDriverName());
		page.addDriverLibrary(driverDefinition.getDriverLibrary());
		finish();
	}

	@Override
	public void finish() {
		new PushButton("OK").click();
	}

}
