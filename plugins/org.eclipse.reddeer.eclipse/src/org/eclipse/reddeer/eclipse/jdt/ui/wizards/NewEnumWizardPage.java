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

import org.eclipse.reddeer.core.reference.ReferencedComposite;
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
	
	public NewEnumWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}
	
	/**
	 * Sets name .
	 *
	 * @param name of enum
	 */
	public NewEnumWizardPage setName(String name){
		new LabeledText(this, "Name:").setText(name);
		return this;
	}
	
	/**
	 * Sets package name.
	 *
	 * @param packageName of enum
	 */
	public NewEnumWizardPage setPackage(String packageName) {
		new LabeledText(this, "Package:").setText(packageName);
		return this;
	}
	
	/**
	 * Sets source folder.
	 *
	 * @param sourceFolder of enum
	 */
	public NewEnumWizardPage setSourceFolder(String sourceFolder){
		new LabeledText(this, "Source folder:").setText(sourceFolder);
		return this;
	}
	
	/**
	 * Check/Uncheck Generate comment.
	 *
	 * @param toggle generate comments checkbox
	 */
	public NewEnumWizardPage toggleGenerateComments(boolean toggle){
		new CheckBox(this, "Generate comments").toggle(toggle);
		return this;
	}
	
	/**
	 * Check/Uncheck Enclosing type.
	 *
	 * @param toggle enclosing type checkbox
	 */
	public NewEnumWizardPage toggleEnclosingType(boolean toggle){
		new CheckBox(this, "Enclosing type").toggle(toggle);
		return this;
	}
	
	/**
	 * Sets enclosing tyoe.
	 *
	 * @param enclosingType of enum
	 */
	public NewEnumWizardPage setEnclosingType(String enclosingType){
		new LabeledText(this, "Enclosing type").setText(enclosingType);
		return this;
	}
	
	/**
	 * Check/Uncheck public modifier.
	 *
	 * @param toggle public modifier radio button
	 */
	public NewEnumWizardPage togglePublicModifier(boolean toggle){
		new RadioButton(this, "public").toggle(toggle);
		return this;
	}
	
	/**
	 * Check/Uncheck default modifier.
	 *
	 * @param toggle default modifier radio button
	 */
	public NewEnumWizardPage toggleDefaultModifier(boolean toggle){
		new RadioButton(this, "default").toggle(toggle);
		return this;
	}
	
	/**
	 * Check/Uncheck private modifier.
	 *
	 * @param toggle private modifier radio button
	 */
	public NewEnumWizardPage togglePrivateModifier(boolean toggle){
		new RadioButton(this, "private").toggle(toggle);
		return this;
	}
	
	/**
	 * Check/Uncheck protected modifier.
	 *
	 * @param toggle protected modifier radio button
	 */
	public NewEnumWizardPage toggleProtectedModifier(boolean toggle){
		new RadioButton(this, "protected").toggle(toggle);
		return this;
	}
	
	/**
	 * Returns enum name.
	 *
	 * @return enum name
	 */
	public String getName(){
		return new LabeledText(this, "Name:").getText();
	}

	/**
	 * Returns package of enum.
	 *
	 * @return package of enum
	 */
	public String getPackage() {
		return new LabeledText(this, "Package:").getText();
	}
	
	/**
	 * Checks if wizard should generate comments.
	 *
	 * @return true if wizard should generate comments, false otherwise
	 */
	public boolean isGenerateComments(){
		return new CheckBox(this, "Generate comments").isChecked();
	}
	
	/**
	 * Checks if enclosing type is checked.
	 *
	 * @return true if enclosing type is checked, false otherwise
	 */
	public boolean isEnclosingType(){
		return new CheckBox(this, "Enclosing type").isChecked();
	}
	
	/**
	 * Returns enclosing type.
	 *
	 * @return enclosing type
	 */
	public String getEnclosingType(){
		return new LabeledText(this, "Enclosing type").getText();
	}
	
	/**
	 * Checks if public modifier is selected.
	 *
	 * @return true if public modifier is selected, false otherwise
	 */
	public boolean isPublicModifier(){
		return new RadioButton(this, "public").isSelected();
	}
	
	/**
	 * Checks if default modifier is selected.
	 *
	 * @return true if default modifier is selected, false otherwise
	 */
	public boolean isDefaultModifier(){
		return new RadioButton(this, "default").isSelected();
	}
	
	/**
	 * Checks if private modifier is selected.
	 *
	 * @return true if private modifier is selected, false otherwise
	 */
	public boolean isPrivateModifier(){
		return new RadioButton(this, "private").isSelected();
	}
	
	/**
	 * Checks if protected modifier is selected.
	 *
	 * @return true if protected modifier is selected, false otherwise
	 */
	public boolean isProtectedModifier(){
		return new RadioButton(this, "protected").isSelected();
	}
}
