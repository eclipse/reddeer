/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Page for selecting a wizard from a group of available wizards.
 * @author rawagner
 *
 */
public abstract class WorkbenchWizardSelectionPage extends WizardPage{
	
	/**
	 * Toggles show all wizards checkbox
	 * @param toggle true to check, false to uncheck
	 */
	public void showAllWizards(boolean toggle){
		new CheckBox("Show All Wizards.").toggle(toggle);
	}
	
	/**
	 * Checks if show all wizards checkbox is checked
	 * @return true if show all wizards checkbox is checked, false otherwise
	 */
	public boolean isShowAllWizards(){
		return new CheckBox("Show All Wizards.").isChecked();
	}
	
	/**
	 * Filters wizard according to specified text
	 * @param text to filter wizards
	 */
	public void filterWizards(String text){
		new LabeledText("Wizards:").setText(text);
	}
	
	/**
	 * Select project
	 * @param projectPath path to project (ie category, project name)
	 */
	public void selectProject(String... projectPath){
		new DefaultTreeItem(projectPath).select();
	}

}
