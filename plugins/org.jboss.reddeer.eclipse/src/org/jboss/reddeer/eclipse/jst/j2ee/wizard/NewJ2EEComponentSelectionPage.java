package org.jboss.reddeer.eclipse.jst.j2ee.wizard;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.eclipse.jst.servlet.ui.WebProjectWizard;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.WidgetIsEnabled;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

public class NewJ2EEComponentSelectionPage extends WizardPage{
	
	public void toggleCreateDefaultModules(boolean toggle){
		new CheckBox("Create default modules").toggle(toggle);
	}
	
	public boolean isCreateDefaultModules(){
		return new CheckBox("Create default modules").isChecked();
	}
	
	/**
	 * Opens new EJB project dialog - not yet supported in eclipse layer
	 * use swt layer in following steps
	 */
	public void addEnterpriseJavaBean(){
		new RadioButton("Enterprise Java Bean").click();
		new PushButton("Next >").click();
		//should return wizard when it will be implemented
	}
	
	public WebProjectWizard addWeb(){
		new RadioButton("Web").click();
		new PushButton("Next >").click();
		return new WebProjectWizard();
	}
	
	/**
	 * Opens new Connector project dialog - not yet supported in eclipse layer
	 * use swt layer in following steps
	 */
	public void addConnector(){
		new RadioButton("Connector").click();
		new PushButton("Next >").click();
		//should return wizard when it will be implemented
	}
	
	/**
	 * Opens new Application Client project dialog - not yet supported in eclipse layer
	 * use swt layer in following steps
	 */
	public void addApplicationClient(){
		new RadioButton("Application Client").click();
		new PushButton("Next >").click();
		//should return wizard when it will be implemented
	}
	
	public void toggleApplicationClientModule(boolean toggle){
		new CheckBox("Application client module").toggle(toggle);
	}
	
	public void toggleEJBModule(boolean toggle){
		new CheckBox("EJB module").toggle(toggle);
	}
	
	public void toggleWebModule(boolean toggle){
		new CheckBox("Web module").toggle(toggle);
	}
	
	public void toggleConnectionModule(boolean toggle){
		new CheckBox("Connector module").toggle(toggle);
	}
	
	public boolean isApplicationClientModule(){
		return new CheckBox("Application client module").isChecked();
	}
	
	public boolean isEJBModule(){
		return new CheckBox("EJB module").isChecked();
	}
	
	public boolean isWebModule(){
		return new CheckBox("Web module").isChecked();
	}
	
	public boolean isConnectionModule(){
		return new CheckBox("Connector module").isChecked();
	}
	
	public void setApplicationClientModuleName(String name){
		new DefaultText(0).setText(name);
	}
	
	public void setEJBModuleName(String name){
		new DefaultText(1).setText(name);
	}
	
	public void setWebModuleName(String name){
		new DefaultText(2).setText(name);
	}
	
	public void setConnectorModuleName(String name){
		new DefaultText(3).setText(name);
	}
	
	public String getApplicationClientModuleName(){
		return new DefaultText(0).getText();
	}
	
	public String getEJBModuleName(){
		return new DefaultText(1).getText();
	}
	
	public String getWebModuleName(){
		return new DefaultText(2).getText();
	}
	
	public String getConnectorModuleName(){
		return new DefaultText(3).getText();
	}
	

	public void finish(){
		new WaitUntil(new WidgetIsEnabled(new PushButton("Finish")));
		new PushButton("Finish").click();
		new WaitWhile(new JobIsRunning());
		new DefaultShell("New EAR Application Project");
		//have to wait otherwise SWT exception is thrown
		AbstractWait.sleep(TimePeriod.getCustom(3));
	}
	
	
}
