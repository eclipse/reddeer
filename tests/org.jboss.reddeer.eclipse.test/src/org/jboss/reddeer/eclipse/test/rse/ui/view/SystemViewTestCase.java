package org.jboss.reddeer.eclipse.test.rse.ui.view;

import org.jboss.reddeer.eclipse.condition.RemoteSystemExists;
import org.jboss.reddeer.eclipse.rse.ui.view.System;
import org.jboss.reddeer.eclipse.rse.ui.view.SystemView;
import org.jboss.reddeer.eclipse.rse.ui.wizard.NewConnectionWizardDialog;
import org.jboss.reddeer.eclipse.rse.ui.wizard.NewConnectionWizardMainPage;
import org.jboss.reddeer.eclipse.rse.ui.wizard.NewConnectionWizardSelectionPage;
import org.jboss.reddeer.eclipse.rse.ui.wizard.NewConnectionWizardSelectionPage.SystemType;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.After;
import org.junit.Ignore;

@Ignore
public class SystemViewTestCase extends RedDeerTest{

	protected SystemView remoteSystemView = new SystemView();
	
	protected NewConnectionWizardDialog wizardDialog;
	
	
	protected void createSystem(String hostname, SystemType type){
		createSystem(hostname, hostname, type);
	}
	
	protected void createSystem(String hostname, String connectionName, SystemType type){
		
		wizardDialog = remoteSystemView.newConnection();
		
		NewConnectionWizardSelectionPage selectionPage = new NewConnectionWizardSelectionPage();
		selectionPage.selectSystemType(type);
		wizardDialog.next();
		
		NewConnectionWizardMainPage mainPage = new NewConnectionWizardMainPage();
		
		mainPage.setHostName(hostname);
		mainPage.setConnectionName(connectionName);
		wizardDialog.finish();
		
		new WaitUntil(new RemoteSystemExists(connectionName));
		
	}
	
	
	@After
	public void tearDown(){
		
		if (wizardDialog != null && NewConnectionWizardDialog.TITLE.equals(new DefaultShell().getText())){
			wizardDialog.cancel();
		}

		for (System system : remoteSystemView.getSystems()){
			system.delete();
		}
		super.tearDown();
	}
}
