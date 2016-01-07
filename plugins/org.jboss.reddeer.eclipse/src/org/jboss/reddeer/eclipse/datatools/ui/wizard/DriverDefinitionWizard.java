/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.datatools.ui.wizard;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.eclipse.datatools.ui.DriverDefinition;
import org.jboss.reddeer.eclipse.datatools.ui.DriverTemplate;
import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * Wizard for creating a new driver definition.
 * 
 * @author apodhrad
 * 
 */
public class DriverDefinitionWizard extends WizardDialog {
	
	public static final String WIZARD_TITLE = "New Driver Definition";
	
	/**
	 * Instantiates a new driver definition wizard.
	 */
	public DriverDefinitionWizard() {
		super();
	}

	/**
	 * Create a given driver definition.
	 *
	 * @param driverDefinition the driver definition
	 */
	public void create(DriverDefinition driverDefinition) {
		DriverTemplate drvTemp = driverDefinition.getDriverTemplate();
		DriverDefinitionPage page = new DriverDefinitionPage();
		page.selectDriverTemplate(drvTemp.getType(), drvTemp.getVersion());
		page.setName(driverDefinition.getDriverName());
		page.addDriverLibrary(driverDefinition.getDriverLibrary());
		finish();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.wizard.WizardDialog#finish()
	 */
	@Override
	public void finish() {		
		new DefaultShell(WIZARD_TITLE);
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(WIZARD_TITLE), TimePeriod.NORMAL);
	}

}
