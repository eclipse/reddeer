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

package org.eclipse.reddeer.ui.test.wizard.impl;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.combo.DefaultCombo;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Page of the RedDeer Test Plug-in wizard
 * @author jrichter
 *
 */
public class RedDeerTestPluginWizardPage extends WizardPage {
	
	public RedDeerTestPluginWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Sets the plug-in name
	 * @param name
	 */
	public void setPluginName(String name) {
		new LabeledText(this, "Plug-in Name:").setText(name);
	}
	
	/**
	 * Sets the plug-in id
	 * @param id
	 */
	public void setPluginId(String id) {
		new LabeledText(this, "Plug-in id:").setText(id);
	}
	
	/**
	 * Sets the plug-in version
	 * @param version
	 */
	public void setVersion(String version) {
		new LabeledText(this, "Version:").setText(version);
	}
	
	/**
	 * Sets the plug-in provider
	 * @param provider
	 */
	public void setProvider(String provider) {
		new LabeledText(this, "Provider:").setText(provider);
	}
	
	/**
	 * Toggles the Example Test checkbox
	 * @param checked true to check, false to uncheck
	 */
	public void toggleExampleTest(boolean checked) {
		new CheckBox(this).toggle(checked);
	}
	
	/**
	 * Toggle the Product radio button
	 * @param isProduct
	 */
	public void setProduct(boolean isProduct) {
		new RadioButton(this, "Product id:").toggle(isProduct);
	}
	
	/**
	 * Toggle the Application radio button
	 * @param isApplication
	 */
	public void setApplication(boolean isApplication) {
		new RadioButton(this, "Application id:").toggle(isApplication);
	}
	
	/**
	 * Select product from combo 
	 * @param product id of the product to select
	 */
	public void selectProduct(String product) {
		new DefaultCombo(this).setSelection(product);
	}
	
	/**
	 * Select application from combo
	 * @param application id of the application to select
	 */
	public void selectApplication(String application) {
		new DefaultCombo(this, 1).setSelection(application);
	}
}
