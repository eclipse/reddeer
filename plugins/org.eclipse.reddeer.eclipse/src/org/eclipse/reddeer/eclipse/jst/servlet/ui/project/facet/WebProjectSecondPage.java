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
package org.eclipse.reddeer.eclipse.jst.servlet.ui.project.facet;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * The second wizard page for creating web project.
 */
public class WebProjectSecondPage extends WizardPage{
	
	public WebProjectSecondPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Edits the source folders on build path.
	 *
	 * @param sourceFolder the source folder
	 * @param newVaule the new vaule
	 */
	public void editSourceFoldersOnBuildPath(String sourceFolder, String newVaule){
		new DefaultTreeItem(new DefaultTree(referencedComposite), sourceFolder).select();
		new PushButton(referencedComposite, "Edit...").click();
		Shell editShell = new DefaultShell("Edit Source Folder");
		new DefaultText(editShell).setText(newVaule);
		new OkButton(editShell).click();
		new WaitWhile(new ShellIsAvailable(editShell));
	}
	
	/**
	 * Removes the source folders on build path.
	 *
	 * @param sourceFolder the source folder
	 */
	public void removeSourceFoldersOnBuildPath(String sourceFolder){
		new DefaultTreeItem(new DefaultTree(referencedComposite), sourceFolder).select();
		new PushButton(referencedComposite, "Remove").click();
	}
	
	/**
	 * Adds the source folders on build path.
	 *
	 * @param newVaule the new vaule
	 */
	public void addSourceFoldersOnBuildPath(String newVaule){
		new PushButton(referencedComposite, "Add Folder...").click();
		Shell addShell = new DefaultShell("Add Source Folder");
		new DefaultText(addShell).setText(newVaule);
		new OkButton(addShell).click();
		new WaitWhile(new ShellIsAvailable(addShell));
	}
	
	/**
	 * Sets the default output folder.
	 *
	 * @param folder the new default output folder
	 */
	public void setDefaultOutputFolder(String folder){
		new LabeledText(referencedComposite, "Default output folder:").setText(folder);
	}
	
	/**
	 * Gets the source folders.
	 *
	 * @return the source folders
	 */
	public List<String> getSourceFolders(){
		List<String> toReturn = new ArrayList<String>();
		for(TreeItem item: new DefaultTree(referencedComposite).getAllItems()){
			toReturn.add(item.getText());
		}
		return toReturn;
	}
	
	
	
	

}
