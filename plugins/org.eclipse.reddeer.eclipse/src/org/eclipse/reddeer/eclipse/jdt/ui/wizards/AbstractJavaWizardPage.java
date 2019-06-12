/*******************************************************************************
 * Copyright (c) 2019 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Abstract class that contains methods for several wizard classes.
 * 
 * @author zcervink@redhat.com
 * 
 */
public abstract class AbstractJavaWizardPage extends WizardPage {

	public AbstractJavaWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given name.
	 * 
	 * @param name Name
	 */
	public AbstractJavaWizardPage setName(String name) {
		new LabeledText(this, "Name:").setText(name);
		return this;
	}

	/**
	 * Returns a given name.
	 * 
	 * @return name name
	 */
	public String getName() {
		return new LabeledText(this, "Name:").getText();
	}

	/**
	 * Sets a given package name.
	 * 
	 * @param packageName Package name
	 */
	public AbstractJavaWizardPage setPackage(String packageName) {
		new LabeledText(this, "Package:").setText(packageName);
		return this;
	}

	/**
	 * Returns a package name.
	 * 
	 * @return package name
	 */
	public String getPackage() {
		return new LabeledText(this, "Package:").getText();
	}

	/**
	 * Check/Uncheck Enclosing type.
	 *
	 * @param toggle enclosing type checkbox
	 */
	public AbstractJavaWizardPage toggleEnclosingTypeCheckbox(boolean toggle) {
		new CheckBox(this, "Enclosing type:").toggle(toggle);
		return this;
	}

	/**
	 * Sets a given source folder.
	 * 
	 * @param sourceFolder Source folder
	 */
	public AbstractJavaWizardPage setSourceFolder(String sourceFolder) {
		new LabeledText(this, "Source folder:").setText(sourceFolder);
		return this;
	}

	/**
	 * Returns a source folder.
	 * 
	 * @return source folder
	 */
	public String getSourceFolder() {
		return new LabeledText(this, "Source folder:").getText();
	}

	/**
	 * Check/Uncheck public modifier.
	 *
	 * @param toggle public modifier radio button
	 */
	public AbstractJavaWizardPage togglePublicModifier(boolean toggle) {
		new RadioButton(this, "public").toggle(toggle);
		return this;
	}

	/**
	 * Check/Uncheck default modifier.
	 *
	 * @param toggle default modifier radio button
	 */
	public AbstractJavaWizardPage toggleDefaultModifier(boolean toggle) {
		new RadioButton(this, "default").toggle(toggle);
		return this;
	}

	/**
	 * Check/Uncheck package modifier.
	 *
	 * @param toggle package modifier radio button
	 */
	public AbstractJavaWizardPage togglePackageModifier(boolean toggle) {
		new RadioButton(this, "package").toggle(toggle);
		return this;
	}

	/**
	 * Check/Uncheck private modifier.
	 *
	 * @param toggle private modifier radio button
	 */
	public AbstractJavaWizardPage togglePrivateModifier(boolean toggle) {
		new RadioButton(this, "private").toggle(toggle);
		return this;
	}

	/**
	 * Check/Uncheck protected modifier.
	 *
	 * @param toggle protected modifier radio button
	 */
	public AbstractJavaWizardPage toggleProtectedModifier(boolean toggle) {
		new RadioButton(this, "protected").toggle(toggle);
		return this;
	}

	/**
	 * Checks if public modifier is selected.
	 *
	 * @return true if public modifier is selected, false otherwise
	 */
	public boolean isPublicModifier() {
		return new RadioButton(this, "public").isSelected();
	}

	/**
	 * Checks if default modifier is selected.
	 *
	 * @return true if default modifier is selected, false otherwise
	 */
	public boolean isDefaultModifier() {
		return new RadioButton(this, "default").isSelected();
	}

	/**
	 * Checks if private modifier is selected.
	 *
	 * @return true if private modifier is selected, false otherwise
	 */
	public boolean isPrivateModifier() {
		return new RadioButton(this, "private").isSelected();
	}

	/**
	 * Checks if protected modifier is selected.
	 *
	 * @return true if protected modifier is selected, false otherwise
	 */
	public boolean isProtectedModifier() {
		return new RadioButton(this, "protected").isSelected();
	}

	/**
	 * Gets an enabled modifier.
	 * 
	 * @param String modifier name
	 */
	public String getCurrentModifier() {
		if (new RadioButton(this, "public").isSelected()) {
			return "public";
		} else if (new RadioButton(this, "package").isSelected()) {
			return "package";
		} else if (new RadioButton(this, "private").isSelected()) {
			return "private";
		} else if (new RadioButton(this, "protected").isSelected()) {
			return "protected";
		}

		throw new RedDeerException(
				"Wizard modifiers property has to be set to 1 of 4 values ('public', 'package', 'private' or 'protected').");
	}

	/**
	 * Check/Uncheck Generate comments.
	 *
	 * @param toggle generate comments checkbox
	 */
	public AbstractJavaWizardPage toggleGenerateCommentsCheckbox(boolean toggle) {
		new CheckBox(this, "Generate comments").toggle(toggle);
		return this;
	}

	/**
	 * Return the generate comments checkbox state.
	 *
	 * @return boolean state of the generate comments checkbox
	 */
	public boolean getGenerateCommentsCheckboxState() {
		return new CheckBox(this, "Generate comments").isChecked();
	}

	/**
	 * Sets enclosing tyoe.
	 *
	 * @param enclosingType of enum
	 */
	public AbstractJavaWizardPage setEnclosingType(String enclosingType) {
		new DefaultText(2).setText(enclosingType);
		return this;
	}

	/**
	 * Returns enclosing type.
	 *
	 * @return enclosing type
	 */
	public String getEnclosingType() {
		return new DefaultText(2).getText();
	}

	/**
	 * Checks if wizard should generate comments.
	 *
	 * @return true if wizard should generate comments, false otherwise
	 */
	public boolean isGenerateCommentsCheckboxChecked() {
		return new CheckBox(this, "Generate comments").isChecked();
	}

	/**
	 * Checks if enclosing type is checked.
	 *
	 * @return true if enclosing type is checked, false otherwise
	 */
	public boolean isEnclosingTypeCheckboxChecked() {
		return new CheckBox(this, "Enclosing type").isChecked();
	}
}
