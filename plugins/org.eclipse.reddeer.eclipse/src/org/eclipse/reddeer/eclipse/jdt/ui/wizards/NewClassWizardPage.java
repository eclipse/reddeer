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
import org.eclipse.reddeer.swt.impl.button.RadioButton;
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
		return getAbstactModifierCheckBox().isChecked();
	}

	/**
	 * Check/Uncheck abstract modifier checkbox.
	 *
	 * @param toggle abstract modifier checkbox
	 */
	public NewClassWizardPage toggleAbstractModifierCheckbox(boolean toggle) {
		getAbstactModifierCheckBox().toggle(toggle);
		return this;
	}

	/**
	 * Return the final modifier checkbox state.
	 *
	 * @return boolean state of the final checkbox
	 */
	public boolean getFinalModifierCheckboxState() {
		return getFinalModifierCheckBox().isChecked();
	}

	/**
	 * Check/Uncheck final modifier checkbox.
	 *
	 * @param toggle final modifier checkbox
	 */
	public NewClassWizardPage toggleFinalModifierCheckbox(boolean toggle) {
		getFinalModifierCheckBox().toggle(toggle);
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
	
	/**
	 * Return "abstract" check box object
	 * @return CheckBox for abstract modifier
	 */
	public CheckBox getAbstactModifierCheckBox() {
		return new CheckBox(this, "abstract");
	}
	
	/**
	 * Return "final" check box object
	 * @return CheckBox for final modifier
	 */
	public CheckBox getFinalModifierCheckBox() {
		return new CheckBox(this, "final");
	}
	
	/**
	 * Return "final" radio button object
	 * @return RadioButton for final modifier
	 */
	public RadioButton getFinalModifierRadioButton() {
		return new RadioButton(this, "final");
	}
	
	/**
	 * Return "none" radio button object
	 * @return RadioButton for none modifier
	 */
	public RadioButton getNoneModifierRadioButton() {
		return new RadioButton(this, "none");
	}
	
	/**
	 * Return "sealed" radio button object
	 * @return RadioButton for sealed modifier
	 */
	public RadioButton getSealedModifierRadioButton() {
		return new RadioButton(this, "sealed");
	}
	
	/**
	 * Return "non-sealed" radio button object
	 * @return RadioButton for non-sealed modifier
	 */
	public RadioButton getNonSealedModifierRadioButton() {
		return new RadioButton(this, "non-sealed");
	}
}
