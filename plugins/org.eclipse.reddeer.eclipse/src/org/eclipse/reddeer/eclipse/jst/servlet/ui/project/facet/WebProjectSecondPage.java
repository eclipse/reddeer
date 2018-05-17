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
	public WebProjectSecondPage editSourceFoldersOnBuildPath(String sourceFolder, String newVaule){
		new DefaultTreeItem(new DefaultTree(this), sourceFolder).select();
		new PushButton(this, "Edit...").click();
		Shell editShell = new DefaultShell("Edit Source Folder");
		new DefaultText(editShell).setText(newVaule);
		new OkButton(editShell).click();
		new WaitWhile(new ShellIsAvailable(editShell));
		return this;
	}
	
	/**
	 * Removes the source folders on build path.
	 *
	 * @param sourceFolder the source folder
	 */
	public WebProjectSecondPage removeSourceFoldersOnBuildPath(String sourceFolder){
		new DefaultTreeItem(new DefaultTree(this), sourceFolder).select();
		new PushButton(this, "Remove").click();
		return this;
	}
	
	/**
	 * Adds the source folders on build path.
	 *
	 * @param newVaule the new vaule
	 */
	public WebProjectSecondPage addSourceFoldersOnBuildPath(String newVaule){
		new PushButton(this, "Add Folder...").click();
		Shell addShell = new DefaultShell("Add Source Folder");
		new DefaultText(addShell).setText(newVaule);
		new OkButton(addShell).click();
		new WaitWhile(new ShellIsAvailable(addShell));
		return this;
	}
	
	/**
	 * Sets the default output folder.
	 *
	 * @param folder the new default output folder
	 */
	public WebProjectSecondPage setDefaultOutputFolder(String folder){
		new LabeledText(this, "Default output folder:").setText(folder);
		return this;
	}
	
	/**
	 * Gets the source folders.
	 *
	 * @return the source folders
	 */
	public List<String> getSourceFolders(){
		List<String> toReturn = new ArrayList<String>();
		for(TreeItem item: new DefaultTree(this).getAllItems()){
			toReturn.add(item.getText());
		}
		return toReturn;
	}
	
	
	
	

}
