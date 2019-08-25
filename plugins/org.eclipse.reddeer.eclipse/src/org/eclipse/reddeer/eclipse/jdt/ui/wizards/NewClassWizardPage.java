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
package org.eclipse.reddeer.eclipse.jdt.ui.wizards;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Wizard page for creating a java class.
 */
public class NewClassWizardPage extends AbstractJavaWizardPage implements CanImplement {

	/**
	 * Instantiates a new new java class wizard page.
	 */
	public NewClassWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets generating static main method.
	 * 
	 * @param setMainMethod Indicates whether to generate static main method
	 */
	public NewClassWizardPage setStaticMainMethod(boolean setMainMethod) {
		new CheckBox(this, "public static void main(String[] args)").toggle(setMainMethod);
		return this;
	}

	/**
	 * Return the create static main method checkbox state.
	 *
	 * @return boolean state of the create static main method checkbox
	 */
	public boolean getStaticMainMethodCheckboxState() {
		return new CheckBox(this, "public static void main(String[] args)").isChecked();
	}

	/**
	 * Return the abstract modifier checkbox state.
	 *
	 * @return boolean state of the abstract checkbox
	 */
	public boolean getAbstractModifierCheckboxState() {
		return new CheckBox(this, "abstract").isChecked();
	}

	/**
	 * Check/Uncheck abstract modifier checkbox.
	 *
	 * @param toggle abstract modifier checkbox
	 */
	public NewClassWizardPage toggleAbstractModifierCheckbox(boolean toggle) {
		new CheckBox(this, "abstract").toggle(toggle);
		return this;
	}

	/**
	 * Return the final modifier checkbox state.
	 *
	 * @return boolean state of the final checkbox
	 */
	public boolean getFinalModifierCheckboxState() {
		return new CheckBox(this, "final").isChecked();
	}

	/**
	 * Check/Uncheck final modifier checkbox.
	 *
	 * @param toggle final modifier checkbox
	 */
	public NewClassWizardPage toggleFinalModifierCheckbox(boolean toggle) {
		new CheckBox(this, "final").toggle(toggle);
		return this;
	}

	/**
	 * Sets a given superclass.
	 * 
	 * @param name superclassName
	 */
	public NewClassWizardPage setSuperclassName(String superclassName) {
		new LabeledText(this, "Superclass:").setText(superclassName);
		return this;
	}

	/**
	 * Returns a superclass.
	 * 
	 * @return Superclass value
	 */
	public String getSuperclassName() {
		return new LabeledText(this, "Superclass:").getText();
	}
}
