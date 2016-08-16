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
package org.jboss.reddeer.eclipse.wst.html.ui.wizard;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;

/**
 * New HTML Templates page
 * @author rawagner
 *
 */
public class NewHTMLTemplatesWizardPage extends WizardPage{
	
	/**
	 * Toggles Use HTML Templates checkbox
	 * @param toggle true to enable checkbox, false otherwise 
	 */
	public void toggleUseHTMLTemplate(boolean toggle){
		new CheckBox("Use HTML Template").toggle(toggle);
	}
	
	/**
	 * Choose template from table
	 * @param template template name
	 */
	public void setTemplate(String template){
		new DefaultTableItem(template).select();
	}
	
	/**
	 * Checks whether HTML template is used
	 * @return true if checkbox Use HTML Template is checked, false otherwise
	 */
	public boolean isUseHTMLTeplate(){
		return new CheckBox("Use HTML Template").isChecked();
	}
	
	/**
	 * @return selected HTML template
	 */
	public String getHTMLTemplate(){
		return new DefaultTable().getSelectetItems().get(0).getText();
	}
	
	

}
