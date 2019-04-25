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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Wizard page for creating a java class.
 */
public class NewClassWizardPage extends AbstractJavaWizardPage {

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

	/**
	 * Add extended interface.
	 * 
	 * @param interfaceName String with name of interface to add
	 */
	public void addExtendedInterface(String interfaceName) {
		new PushButton("Add...").click();
		new DefaultShell("Implemented Interfaces Selection");
		new DefaultText(0).setText(interfaceName);
		AbstractWait.sleep(TimePeriod.getCustom(2));

		switch (new DefaultTable(0).getItems().size()) {
		case 0:
			throw new RedDeerException("No item was found for given interface name '" + interfaceName + "'.");
		case 1:
			new PushButton("OK").click();
			break;
		default:
			throw new RedDeerException("More than 1 item was found for given interface name '" + interfaceName + "'.");
		}
	}

	/**
	 * Remove extended interface.
	 * 
	 * @param interfaceName String with name of interface to remove
	 */
	public void removeExtendedInterface(String interfaceName) {
		DefaultTable table = new DefaultTable(0);
		table.getItem(interfaceName).select();
		new PushButton("Remove").click();
	}

	/**
	 * Returns list of names of extended interfaces.
	 * 
	 * @return List of extended interfaces
	 */
	public ArrayList<String> getExtendedInterfaces() {
		DefaultTable table = new DefaultTable(0);
		List<TableItem> tableItems = table.getItems();
		ArrayList<String> tableItemNames = new ArrayList<String>();

		for (TableItem item : tableItems) {
			tableItemNames.add(item.getText());
		}

		return tableItemNames;
	}
}
