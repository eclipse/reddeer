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
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represent first page of enum wizard
 * @author rawagner
 *
 */
public class NewEnumWizardPage extends WizardPage{
	
	/**
	 * Sets name .
	 *
	 * @param name of enum
	 */
	public void setName(String name){
		new LabeledText("Name:").setText(name);
	}
	
	/**
	 * Sets package name.
	 *
	 * @param packageName of enum
	 */
	public void setPackage(String packageName) {
		new LabeledText("Package:").setText(packageName);
	}
	
	/**
	 * Sets source folder.
	 *
	 * @param sourceFolder of enum
	 */
	public void setSourceFolder(String sourceFolder){
		new LabeledText("Source folder:").setText(sourceFolder);
	}
	
	/**
	 * Check/Uncheck Generate comment.
	 *
	 * @param toggle generate comments checkbox
	 */
	public void toggleGenerateComments(boolean toggle){
		new CheckBox("Generate comments").toggle(toggle);
	}
	
	/**
	 * Check/Uncheck Enclosing type.
	 *
	 * @param toggle enclosing type checkbox
	 */
	public void toggleEnclosingType(boolean toggle){
		new CheckBox("Enclosing type").toggle(toggle);
	}
	
	/**
	 * Sets enclosing tyoe.
	 *
	 * @param enclosingType of enum
	 */
	public void setEnclosingType(String enclosingType){
		new LabeledText("Enclosing type").setText(enclosingType);
	}
	
	/**
	 * Check/Uncheck public modifier.
	 *
	 * @param toggle public modifier radio button
	 */
	public void togglePublicModifier(boolean toggle){
		new RadioButton("public").toggle(toggle);
	}
	
	/**
	 * Check/Uncheck default modifier.
	 *
	 * @param toggle default modifier radio button
	 */
	public void toggleDefaultModifier(boolean toggle){
		new RadioButton("default").toggle(toggle);
	}
	
	/**
	 * Check/Uncheck private modifier.
	 *
	 * @param toggle private modifier radio button
	 */
	public void togglePrivateModifier(boolean toggle){
		new RadioButton("private").toggle(toggle);
	}
	
	/**
	 * Check/Uncheck protected modifier.
	 *
	 * @param toggle protected modifier radio button
	 */
	public void toggleProtectedModifier(boolean toggle){
		new RadioButton("protected").toggle(toggle);
	}
	
	/**
	 * Returns enum name.
	 *
	 * @return enum name
	 */
	public String getName(){
		return new LabeledText("Name:").getText();
	}

	/**
	 * Returns package of enum.
	 *
	 * @return package of enum
	 */
	public String getPackage() {
		return new LabeledText("Package:").getText();
	}
	
	/**
	 * Checks if wizard should generate comments.
	 *
	 * @return true if wizard should generate comments, false otherwise
	 */
	public boolean isGenerateComments(){
		return new CheckBox("Generate comments").isChecked();
	}
	
	/**
	 * Checks if enclosing type is checked.
	 *
	 * @return true if enclosing type is checked, false otherwise
	 */
	public boolean isEnclosingType(){
		return new CheckBox("Enclosing type").isChecked();
	}
	
	/**
	 * Returns enclosing type.
	 *
	 * @return enclosing type
	 */
	public String getEnclosingType(){
		return new LabeledText("Enclosing type").getText();
	}
	
	/**
	 * Checks if public modifier is selected.
	 *
	 * @return true if public modifier is selected, false otherwise
	 */
	public boolean isPublicModifier(){
		return new RadioButton("public").isSelected();
	}
	
	/**
	 * Checks if default modifier is selected.
	 *
	 * @return true if default modifier is selected, false otherwise
	 */
	public boolean isDefaultModifier(){
		return new RadioButton("default").isSelected();
	}
	
	/**
	 * Checks if private modifier is selected.
	 *
	 * @return true if private modifier is selected, false otherwise
	 */
	public boolean isPrivateModifier(){
		return new RadioButton("private").isSelected();
	}
	
	/**
	 * Checks if protected modifier is selected.
	 *
	 * @return true if protected modifier is selected, false otherwise
	 */
	public boolean isProtectedModifier(){
		return new RadioButton("protected").isSelected();
	}
}
