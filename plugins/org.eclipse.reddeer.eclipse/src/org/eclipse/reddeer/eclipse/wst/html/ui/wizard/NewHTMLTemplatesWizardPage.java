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
package org.eclipse.reddeer.eclipse.wst.html.ui.wizard;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;

/**
 * New HTML Templates page
 * @author rawagner
 *
 */
public class NewHTMLTemplatesWizardPage extends WizardPage{
	
	public NewHTMLTemplatesWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Toggles Use HTML Templates checkbox
	 * @param toggle true to enable checkbox, false otherwise 
	 */
	public NewHTMLTemplatesWizardPage toggleUseHTMLTemplate(boolean toggle){
		new CheckBox(this, "Use HTML Template").toggle(toggle);
		return this;
	}
	
	/**
	 * Choose template from table
	 * @param template template name
	 */
	public NewHTMLTemplatesWizardPage setTemplate(String template){
		new DefaultTableItem(this, template).select();
		return this;
	}
	
	/**
	 * Checks whether HTML template is used
	 * @return true if checkbox Use HTML Template is checked, false otherwise
	 */
	public boolean isUseHTMLTeplate(){
		return new CheckBox(this, "Use HTML Template").isChecked();
	}
	
	/**
	 * @return selected HTML template
	 */
	public String getHTMLTemplate(){
		return new DefaultTable(this).getSelectedItems().get(0).getText();
	}
	
	

}
