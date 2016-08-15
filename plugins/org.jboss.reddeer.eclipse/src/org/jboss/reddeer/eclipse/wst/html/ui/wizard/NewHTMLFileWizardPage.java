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

import java.util.List;

import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * New HTML File Wizard page
 * @author rawagner
 *
 */
public class NewHTMLFileWizardPage extends WizardPage{
	
	/**
	 * Enter parent folder
	 * @param parentFolder to be set
	 */
	public void enterParentFolder(String parentFolder){
		new LabeledText("Enter or select the parent folder:").setText(parentFolder);
	}
	
	/**
	 * Select parent folder
	 * @param path to be selected
	 */
	public void selectParentFolder(String... path){
		new DefaultTreeItem(path).select();
	}
	
	/**
	 * Set HTML page name
	 * @param fileName name of page
	 */
	public void setFileName(String fileName){
		new LabeledText("File name:").setText(fileName);
	}
	
	/**
	 * Return parent folder name
	 * @return parent folder name
	 */
	public String getParentFolder(){
		return new LabeledText("Enter or select the parent folder:").getText();
	}
	
	/**
	 * 
	 * @return selected parent folder
	 */
	public TreeItem getSelectedParentFolder(){
		List<TreeItem> ti = new DefaultTree().getSelectedItems();
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
		return new LabeledText("File name:").getText();
	}

}
