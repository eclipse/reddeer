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
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * Wizard page for creating a java interface.
 * 
 * @author zcervink@redhat.com
 * 
 */
public class NewInterfaceCreationWizardPage extends AbstractJavaWizardPage {

	/**
	 * Instantiates a new new java interface wizard page.
	 */
	public NewInterfaceCreationWizardPage(ReferencedComposite referencedComposite) {
		super(referencedComposite);
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
}
