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
package org.jboss.reddeer.eclipse.jdt.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellIsAvailable;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.combo.LabeledCombo;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;

/**
 * Represents Generate HashCode And Equals dialog
 * @author rawagner
 *
 */
public class GenerateHashCodeEqualsDialog {
	
	private Shell shell;
	
	/**
	 * Opens generate hashcode and equals dialog.
	 *
	 * @param viaShellMenu true if dialog should be opened via shell menu,
	 * 	false if context menu should be used
	 */
	public void open(boolean viaShellMenu){
		if(viaShellMenu){
			new ShellMenu("Source","Generate hashCode() and equals()...").select();
		} else {
			new ContextMenu("Source","Generate hashCode() and equals()...").select();
		}
		shell = new DefaultShell("Generate hashCode() and equals()");
	}
	
	/**
	 * Returns class fields.
	 *
	 * @return list of class fields
	 */
	public List<ClassField> getFields(){
		List<ClassField> fields = new ArrayList<ClassField>();
		for(TreeItem i: new DefaultTree().getAllItems()){
			fields.add(new ClassField(i));
		}
		return fields;
	}
	
	/**
	 * Returns possible insertion points.
	 *
	 * @return list of possible insertion points
	 */
	public List<String> getInsertionPoint(){
		return new LabeledCombo("Insertion points:").getItems();
	}
	
	/**
	 * Sets insertion point.
	 *
	 * @param insertionPoint to set
	 */
	public void setInsertionPoint(String insertionPoint){
		new LabeledCombo("Insertion points:").setSelection(insertionPoint);
	}
	
	/**
	 * Checks if dialog should generate comments.
	 *
	 * @return true if dialog should generate comments, false otherwise
	 */
	public boolean isGenerateMethodComments(){
		return new CheckBox("Generate method comments").isChecked();
	}
	
	/**
	 * Checks if dialog should use instanceof.
	 *
	 * @return true if dialog should use instanceof, false otherwise
	 */
	public boolean isUseInstanceofToCompareTypes(){
		return new CheckBox("Use 'instanceof' to compare types").isChecked();
	}
	
	/**
	 * Checks if dialog should use blocks in if statements.
	 *
	 * @return true if dialog should use blocks in if statements, false otherwise
	 */
	public boolean isUseBlocksInIfStatements(){
		return new CheckBox("Use blocks in 'if' statements").isChecked();
	}
	
	/**
	 * Check/Uncheck if dialog should generate comments.
	 *
	 * @param toggle true if dialog should generate comments, false otherwise
	 */
	public void toggleGenerateMethodComments(boolean toggle){
		new CheckBox("Generate method comments").toggle(toggle);
	}
	
	/**
	 * Check/Uncheck to use instanceof.
	 *
	 * @param toggle true if dialog should use instanceof, false otherwise
	 */
	public void toggleUseInstanceofToCompareTypes(boolean toggle){
		new CheckBox("Use 'instanceof' to compare types").toggle(toggle);
	}
	
	/**
	 * Check/Uncheck if dialog should use blocks in if statements.
	 *
	 * @param toggle true if dialog should use blocks in if statements, false otherwise
	 */
	public void toggleUseBlocksInIfStatements(boolean toggle){
		new CheckBox("Use blocks in 'if' statements").toggle(toggle);
	}
	
	/**
	 * Selects all class fields.
	 */
	public void selectAll(){
		new PushButton("Select All").click();
	}
	
	/**
	 * Deselects all class fields.
	 */
	public void deselectAll(){
		new PushButton("Deselect All").click();
	}
	
	/**
	 * Press ok.
	 */
	public void ok(){
		String shellText = shell.getText();
		new PushButton("OK").click();
		new WaitWhile(new ShellIsAvailable(shellText));
	}
	
	/**
	 * Press cancel.
	 */
	public void cancel(){
		String shellText = shell.getText();
		new PushButton("Cancel").click();
		new WaitWhile(new ShellIsAvailable(shellText));
	}
	
	
}
