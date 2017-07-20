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
package org.eclipse.reddeer.eclipse.jst.j2ee.wizard;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet.WebProjectWizard;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

public class NewJ2EEComponentSelectionPage extends WizardPage{
	
	public NewJ2EEComponentSelectionPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Toggle create default modules.
	 *
	 * @param toggle the toggle
	 */
	public NewJ2EEComponentSelectionPage toggleCreateDefaultModules(boolean toggle){
		new CheckBox(referencedComposite, "Create default modules").toggle(toggle);
		return this;
	}
	
	/**
	 * Checks if is creates the default modules.
	 *
	 * @return true, if is creates the default modules
	 */
	public boolean isCreateDefaultModules(){
		return new CheckBox(referencedComposite, "Create default modules").isChecked();
	}
	
	/**
	 * Opens new EJB project dialog - not yet supported in eclipse layer
	 * use swt layer in following steps.
	 */
	public void addEnterpriseJavaBean(){
		new RadioButton(referencedComposite, "Enterprise Java Bean").click();
		new PushButton(referencedComposite, "Next >").click();
		//should return wizard when it will be implemented
	}
	
	/**
	 * Adds the web.
	 *
	 * @return the web project wizard
	 */
	public WebProjectWizard addWeb(){
		new RadioButton(referencedComposite, "Web").click();
		new PushButton(referencedComposite, "Next >").click();
		return new WebProjectWizard();
	}
	
	/**
	 * Opens new Connector project dialog - not yet supported in eclipse layer
	 * use swt layer in following steps.
	 */
	public void addConnector(){
		new RadioButton(referencedComposite, "Connector").click();
		new PushButton(referencedComposite, "Next >").click();
		//should return wizard when it will be implemented
	}
	
	/**
	 * Opens new Application Client project dialog - not yet supported in eclipse layer
	 * use swt layer in following steps.
	 */
	public void addApplicationClient(){
		new RadioButton(referencedComposite, "Application Client").click();
		new PushButton(referencedComposite, "Next >").click();
		//should return wizard when it will be implemented
	}
	
	/**
	 * Toggle application client module.
	 *
	 * @param toggle the toggle
	 */
	public NewJ2EEComponentSelectionPage toggleApplicationClientModule(boolean toggle){
		new CheckBox(referencedComposite, "Application client module").toggle(toggle);
		return this;
	}
	
	/**
	 * Toggle ejb module.
	 *
	 * @param toggle the toggle
	 */
	public NewJ2EEComponentSelectionPage toggleEJBModule(boolean toggle){
		new CheckBox(referencedComposite, "EJB module").toggle(toggle);
		return this;
	}
	
	/**
	 * Toggle web module.
	 *
	 * @param toggle the toggle
	 */
	public NewJ2EEComponentSelectionPage toggleWebModule(boolean toggle){
		new CheckBox(referencedComposite, "Web module").toggle(toggle);
		return this;
	}
	
	/**
	 * Toggle connection module.
	 *
	 * @param toggle the toggle
	 */
	public NewJ2EEComponentSelectionPage toggleConnectionModule(boolean toggle){
		new CheckBox(referencedComposite, "Connector module").toggle(toggle);
		return this;
	}
	
	/**
	 * Checks if is application client module.
	 *
	 * @return true, if is application client module
	 */
	public boolean isApplicationClientModule(){
		return new CheckBox(referencedComposite, "Application client module").isChecked();
	}
	
	/**
	 * Checks if is EJB module.
	 *
	 * @return true, if is EJB module
	 */
	public boolean isEJBModule(){
		return new CheckBox(referencedComposite, "EJB module").isChecked();
	}
	
	/**
	 * Checks if is web module.
	 *
	 * @return true, if is web module
	 */
	public boolean isWebModule(){
		return new CheckBox(referencedComposite, "Web module").isChecked();
	}
	
	/**
	 * Checks if is connection module.
	 *
	 * @return true, if is connection module
	 */
	public boolean isConnectionModule(){
		return new CheckBox(referencedComposite, "Connector module").isChecked();
	}
	
	/**
	 * Sets the application client module name.
	 *
	 * @param name the new application client module name
	 */
	public NewJ2EEComponentSelectionPage setApplicationClientModuleName(String name){
		new DefaultText(referencedComposite, 0).setText(name);
		return this;
	}
	
	/**
	 * Sets the EJB module name.
	 *
	 * @param name the new EJB module name
	 */
	public NewJ2EEComponentSelectionPage setEJBModuleName(String name){
		new DefaultText(referencedComposite, 1).setText(name);
		return this;
	}
	
	/**
	 * Sets the web module name.
	 *
	 * @param name the new web module name
	 */
	public NewJ2EEComponentSelectionPage setWebModuleName(String name){
		new DefaultText(referencedComposite, 2).setText(name);
		return this;
	}
	
	/**
	 * Sets the connector module name.
	 *
	 * @param name the new connector module name
	 */
	public NewJ2EEComponentSelectionPage setConnectorModuleName(String name){
		new DefaultText(referencedComposite, 3).setText(name);
		return this;
	}
	
	/**
	 * Gets the application client module name.
	 *
	 * @return the application client module name
	 */
	public String getApplicationClientModuleName(){
		return new DefaultText(referencedComposite, 0).getText();
	}
	
	/**
	 * Gets the EJB module name.
	 *
	 * @return the EJB module name
	 */
	public String getEJBModuleName(){
		return new DefaultText(referencedComposite, 1).getText();
	}
	
	/**
	 * Gets the web module name.
	 *
	 * @return the web module name
	 */
	public String getWebModuleName(){
		return new DefaultText(referencedComposite, 2).getText();
	}
	
	/**
	 * Gets the connector module name.
	 *
	 * @return the connector module name
	 */
	public String getConnectorModuleName(){
		return new DefaultText(referencedComposite, 3).getText();
	}
	
	
}
