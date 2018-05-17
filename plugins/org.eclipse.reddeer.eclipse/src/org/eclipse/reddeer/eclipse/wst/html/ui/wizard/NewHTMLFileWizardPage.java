/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.wst.html.ui.wizard;

import java.util.List;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * New HTML File Wizard page
 * @author rawagner
 *
 */
public class NewHTMLFileWizardPage extends WizardPage{
	
	public NewHTMLFileWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Enter parent folder
	 * @param parentFolder to be set
	 */
	public NewHTMLFileWizardPage enterParentFolder(String parentFolder){
		new LabeledText(this, "Enter or select the parent folder:").setText(parentFolder);
		return this;
	}
	
	/**
	 * Select parent folder
	 * @param path to be selected
	 */
	public NewHTMLFileWizardPage selectParentFolder(String... path){
		new DefaultTreeItem(new DefaultTree(this), path).select();
		return this;
	}
	
	/**
	 * Set HTML page name
	 * @param fileName name of page
	 */
	public NewHTMLFileWizardPage setFileName(String fileName){
		new LabeledText(this, "File name:").setText(fileName);
		return this;
	}
	
	/**
	 * Return parent folder name
	 * @return parent folder name
	 */
	public String getParentFolder(){
		return new LabeledText(this, "Enter or select the parent folder:").getText();
	}
	
	/**
	 * 
	 * @return selected parent folder
	 */
	public TreeItem getSelectedParentFolder(){
		List<TreeItem> ti = new DefaultTree(this).getSelectedItems();
		if(ti.isEmpty()){
			return null;
		}
		return ti.get(0);
	}
	
	/**
	 * 
	 * @return HTML page name
	 */
	public String getFileName(){
		return new LabeledText(this, "File name:").getText();
	}

}
