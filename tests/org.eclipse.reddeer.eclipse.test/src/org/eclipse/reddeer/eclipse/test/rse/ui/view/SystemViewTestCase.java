/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.test.rse.ui.view;

import org.eclipse.reddeer.eclipse.condition.RemoteSystemExists;
import org.eclipse.reddeer.eclipse.rse.ui.view.System;
import org.eclipse.reddeer.eclipse.rse.ui.view.SystemViewPart;
import org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection.RSEDefaultNewConnectionWizardMainPage;
import org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection.RSEMainNewConnectionWizard;
import org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection.RSENewConnectionWizardSelectionPage;
import org.eclipse.reddeer.eclipse.rse.ui.wizards.newconnection.RSENewConnectionWizardSelectionPage.SystemType;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.junit.After;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@Ignore
@RunWith(RedDeerSuite.class)
public class SystemViewTestCase {

	protected SystemViewPart remoteSystemView = new SystemViewPart();
	
	protected RSEMainNewConnectionWizard wizardDialog;
	
	
	protected void createSystem(String hostname, SystemType type){
		createSystem(hostname, hostname, type);
	}
	
	protected void createSystem(String hostname, String connectionName, SystemType type){
		
		remoteSystemView.open();
		wizardDialog = remoteSystemView.newConnection();
		
		RSENewConnectionWizardSelectionPage selectionPage = new RSENewConnectionWizardSelectionPage();
		selectionPage.selectSystemType(type);
		wizardDialog.next();
		
		RSEDefaultNewConnectionWizardMainPage mainPage = new RSEDefaultNewConnectionWizardMainPage();
		
		mainPage.setHostName(hostname);
		mainPage.setConnectionName(connectionName);
		wizardDialog.finish();
		
		new WaitUntil(new RemoteSystemExists(connectionName));
		
	}
	
	
	@After
	public void tearDown(){
		
		if (wizardDialog != null && RSEMainNewConnectionWizard.TITLE.equals(new DefaultShell().getText())){
			wizardDialog.cancel();
		}

		for (System system : remoteSystemView.getSystems()){
			system.delete();
		}
	}
}
