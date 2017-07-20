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
package org.eclipse.reddeer.eclipse.ui.dialogs;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Page for selecting a wizard from a group of available wizards.
 * @author rawagner
 *
 */
public abstract class WorkbenchWizardSelectionPage extends WizardPage{
	
	public WorkbenchWizardSelectionPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Toggles show all wizards checkbox
	 * @param toggle true to check, false to uncheck
	 */
	public WorkbenchWizardSelectionPage showAllWizards(boolean toggle){
		new CheckBox(referencedComposite, "Show All Wizards.").toggle(toggle);
		return this;
	}
	
	/**
	 * Checks if show all wizards checkbox is checked
	 * @return true if show all wizards checkbox is checked, false otherwise
	 */
	public boolean isShowAllWizards(){
		return new CheckBox(referencedComposite, "Show All Wizards.").isChecked();
	}
	
	/**
	 * Filters wizard according to specified text
	 * @param text to filter wizards
	 */
	public WorkbenchWizardSelectionPage filterWizards(String text){
		new LabeledText(referencedComposite, "Wizards:").setText(text);
		return this;
	}
	
	/**
	 * Select project
	 * @param projectPath path to project (ie category, project name)
	 */
	public void selectProject(String... projectPath){
		new DefaultTreeItem(new DefaultTree(referencedComposite), projectPath).select();
	}

}
