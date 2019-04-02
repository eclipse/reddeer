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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.wizard.WizardPage;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Wizard page for creating a java interface.
 * 
 * @author zcervink@redhat.com
 * 
 */
public class NewInterfaceCreationWizardPage extends WizardPage {

	/**
	 * Instantiates a new new java interface wizard page.
	 */
	public NewInterfaceCreationWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
	}

	/**
	 * Sets a given name.
	 * 
	 * @param name Name
	 */
	public NewInterfaceCreationWizardPage setName(String name) {
		new LabeledText(this, "Name:").setText(name);
		return this;
	}

	/**
	 * Sets a given package name.
	 * 
	 * @param packageName Package name
	 */
	public NewInterfaceCreationWizardPage setPackage(String packageName) {
		new LabeledText(this, "Package:").setText(packageName);
		return this;
	}

	/**
	 * Sets a given source folder.
	 * 
	 * @param sourceFolder Source folder
	 */
	public NewInterfaceCreationWizardPage setSourceFolder(String sourceFolder) {
		new LabeledText(this, "Source folder:").setText(sourceFolder);
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
	 * Returns a interface name.
	 * 
	 * @return interface name
	 */
	public String getName() {
		return new LabeledText(this, "Name:").getText();
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
	 * Check/Uncheck Enclosing type.
	 *
	 * @param toggle enclosing type checkbox
	 */
	public NewInterfaceCreationWizardPage toggleEnclosingTypeCheckbox(boolean toggle) {
		new CheckBox(this, "Enclosing type:").toggle(toggle);
		return this;
	}

	/**
	 * Returns Enclosing type.
	 * 
	 * @return enclosing type
	 */
	public String getEnclosingType() {
		return new DefaultText(2).getText();
	}

	/**
	 * Sets a given Enclosing type.
	 * 
	 * @param enclosingTypeName enclosing type
	 */
	public NewInterfaceCreationWizardPage setEnclosingType(String enclosingTypeName) {
		new DefaultText(2).setText(enclosingTypeName);
		return this;
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
				"New Java Interface wizard modifiers property has to be set to 1 of 4 values ('public', 'package', 'private' or 'protected').");
	}

	/**
	 * Sets modifier to public.
	 * 
	 */
	public NewInterfaceCreationWizardPage setPublicModifier() {
		new RadioButton(this, "public").toggle(true);
		return this;
	}

	/**
	 * Sets modifier to package.
	 * 
	 */
	public NewInterfaceCreationWizardPage setPackageModifier() {
		new RadioButton(this, "package").toggle(true);
		return this;
	}

	/**
	 * Sets modifier to private.
	 * 
	 */
	public NewInterfaceCreationWizardPage setPrivateModifier() {
		new RadioButton(this, "private").toggle(true);
		return this;
	}

	/**
	 * Sets modifier to protected.
	 * 
	 */
	public NewInterfaceCreationWizardPage setProtectedModifier() {
		new RadioButton(this, "protected").toggle(true);
		return this;
	}

	/**
	 * Add extended interface.
	 * 
	 * @param interfaceName String with name of interface to add
	 */
	public void addExtendedInterface(String interfaceName) {
		new PushButton("Add...").click();
		new DefaultShell("Extended Interfaces Selection");
		new DefaultText(0).setText(interfaceName);

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

	/**
	 * Check/Uncheck Generate comments.
	 *
	 * @param toggle generate comments checkbox
	 */
	public NewInterfaceCreationWizardPage toggleGenerateCommentsCheckbox(boolean toggle) {
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
}
