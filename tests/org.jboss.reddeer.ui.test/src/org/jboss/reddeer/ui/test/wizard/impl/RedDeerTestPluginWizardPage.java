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

package org.jboss.reddeer.ui.test.wizard.impl;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.text.LabeledText;

/**
 * Page of the RedDeer Test Plug-in wizard
 * @author jrichter
 *
 */
public class RedDeerTestPluginWizardPage extends WizardPage {
	
	/**
	 * Sets the plug-in name
	 * @param name
	 */
	public void setPluginName(String name) {
		new LabeledText("Plug-in Name:").setText(name);
	}
	
	/**
	 * Sets the plug-in id
	 * @param id
	 */
	public void setPluginId(String id) {
		new LabeledText("Plug-in id:").setText(id);
	}
	
	/**
	 * Sets the plug-in version
	 * @param version
	 */
	public void setVersion(String version) {
		new LabeledText("Version:").setText(version);
	}
	
	/**
	 * Sets the plug-in provider
	 * @param provider
	 */
	public void setProvider(String provider) {
		new LabeledText("Provider:").setText(provider);
	}
	
	/**
	 * Toggles the Example Test checkbox
	 * @param checked true to check, false to uncheck
	 */
	public void toggleExampleTest(boolean checked) {
		new CheckBox().toggle(checked);
	}
	
	/**
	 * Toggle the Product radio button
	 * @param isProduct
	 */
	public void setProduct(boolean isProduct) {
		new RadioButton("Product id:").toggle(isProduct);
	}
	
	/**
	 * Toggle the Application radio button
	 * @param isApplication
	 */
	public void setApplication(boolean isApplication) {
		new RadioButton("Application id:").toggle(isApplication);
	}
	
	/**
	 * Select product from combo 
	 * @param product id of the product to select
	 */
	public void selectProduct(String product) {
		new DefaultCombo().setSelection(product);
	}
	
	/**
	 * Select application from combo
	 * @param application id of the application to select
	 */
	public void selectApplication(String application) {
		new DefaultCombo(1).setSelection(application);
	}
}
