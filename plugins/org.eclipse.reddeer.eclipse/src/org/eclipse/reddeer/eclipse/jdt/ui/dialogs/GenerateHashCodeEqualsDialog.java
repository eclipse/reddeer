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
package org.eclipse.reddeer.eclipse.jdt.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.jface.window.AbstractWindow;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.combo.LabeledCombo;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuOpenable;

/**
 * Represents Generate HashCode And Equals dialog
 * @author rawagner
 *
 */
public class GenerateHashCodeEqualsDialog extends AbstractWindow {
	
	public static final String[] MENU = new String[] {"Source", "Generate hashCode() and equals()..."};
	public static final String DIALOG_TEXT = "Generate hashCode() and equals()";
	
	/**
	 * Opens generate hashcode and equals dialog.
	 *
	 * @param viaShellMenu true if dialog should be opened via shell menu,
	 * 	false if context menu should be used
	 * @deprecated use open() and/or setOpenAction()
	 */
	public void open(boolean viaShellMenu){
		if(viaShellMenu){
			new ShellMenuItem(MENU).select();
		} else {
			new ContextMenuItem(MENU).select();
		}
		setShell(new DefaultShell("Generate hashCode() and equals()"));
	}
	
	/**
	 * Returns class fields.
	 *
	 * @return list of class fields
	 */
	public List<ClassField> getFields(){
		List<ClassField> fields = new ArrayList<ClassField>();
		for(TreeItem i: new DefaultTree(shell).getAllItems()){
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
		return new LabeledCombo(shell,"Insertion points:").getItems();
	}
	
	/**
	 * Sets insertion point.
	 *
	 * @param insertionPoint to set
	 */
	public void setInsertionPoint(String insertionPoint){
		new LabeledCombo(shell,"Insertion points:").setSelection(insertionPoint);
	}
	
	/**
	 * Checks if dialog should generate comments.
	 *
	 * @return true if dialog should generate comments, false otherwise
	 */
	public boolean isGenerateMethodComments(){
		return new CheckBox(shell,"Generate method comments").isChecked();
	}
	
	/**
	 * Checks if dialog should use instanceof.
	 *
	 * @return true if dialog should use instanceof, false otherwise
	 */
	public boolean isUseInstanceofToCompareTypes(){
		return new CheckBox(shell,"Use 'instanceof' to compare types").isChecked();
	}
	
	/**
	 * Checks if dialog should use blocks in if statements.
	 *
	 * @return true if dialog should use blocks in if statements, false otherwise
	 */
	public boolean isUseBlocksInIfStatements(){
		return new CheckBox(shell,"Use blocks in 'if' statements").isChecked();
	}
	
	/**
	 * Check/Uncheck if dialog should generate comments.
	 *
	 * @param toggle true if dialog should generate comments, false otherwise
	 */
	public void toggleGenerateMethodComments(boolean toggle){
		new CheckBox(shell,"Generate method comments").toggle(toggle);
	}
	
	/**
	 * Check/Uncheck to use instanceof.
	 *
	 * @param toggle true if dialog should use instanceof, false otherwise
	 */
	public void toggleUseInstanceofToCompareTypes(boolean toggle){
		new CheckBox(shell,"Use 'instanceof' to compare types").toggle(toggle);
	}
	
	/**
	 * Check/Uncheck if dialog should use blocks in if statements.
	 *
	 * @param toggle true if dialog should use blocks in if statements, false otherwise
	 */
	public void toggleUseBlocksInIfStatements(boolean toggle){
		new CheckBox(shell,"Use blocks in 'if' statements").toggle(toggle);
	}
	
	/**
	 * Selects all class fields.
	 */
	public void selectAll(){
		new PushButton(shell,"Select All").click();
	}
	
	/**
	 * Deselects all class fields.
	 */
	public void deselectAll(){
		new PushButton(shell,"Deselect All").click();
	}
	
	/**
	 * Press ok.
	 */
	public void ok(){
		WidgetIsFound generateButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class, shell.getSWTWidget(),
				new WithMnemonicTextMatcher("Generate"));
		
		Button button;
		if(generateButton.test()){
			button = new PushButton((org.eclipse.swt.widgets.Button)generateButton.getResult()); //photon changed button text
		} else {
			button = new OkButton(shell);	
		}
		button.click();
		new WaitWhile(new ShellIsAvailable(shell));
	}
	
	/**
	 * Press cancel.
	 */
	public void cancel(){
		new PushButton(shell, "Cancel").click();
		new WaitWhile(new ShellIsAvailable(shell));
	}

	@Override
	public Openable getDefaultOpenAction() {
		return new WorkbenchMenuOpenable(DIALOG_TEXT, MENU);
	}
	
	
}
