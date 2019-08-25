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
package org.eclipse.reddeer.eclipse.jdt.debug.ui.jres;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.jdt.ui.preferences.JREItem;
import org.eclipse.reddeer.eclipse.jdt.ui.preferences.internal.StandardVMPage;
import org.eclipse.reddeer.eclipse.jdt.ui.preferences.internal.VMTypePage;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.jface.wizard.WizardDialog;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;

/**
 * Class representing JRE Preference page (Java &gt; Installed JREs).
 * 
 * @author rhopp, odockal
 *
 */

public class JREsPreferencePage extends PreferencePage {

	/**
	 * Instantiates a new JR es preference page.
	 */
	public JREsPreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] { "Java", "Installed JREs" });
	}

	/**
	 * Adds JRE.
	 * 
	 * @param path
	 *            path to JRE.
	 * @throws EclipseLayerException
	 *             if Add JRE wizard cannot be completed.
	 */

	public JREsPreferencePage addJRE(String path) {
		addJRE(path, null);
		return this;
	}

	/**
	 * Adds JRE.
	 * 
	 * @param path
	 *            path to JRE.
	 * @param name
	 *            name of the JRE
	 * @throws EclipseLayerException
	 *             if Add JRE wizard cannot be completed.
	 */

	public JREsPreferencePage addJRE(String path, String name) {
		WizardDialog wizard = openAddJREWizard();
		StandardVMPage vmPage = fillJREDialog(wizard, path, name);
		if (!wizard.isFinishEnabled()) {
			String errorMessage = vmPage.getErrorMessage();
			wizard.cancel();
			throw new EclipseLayerException(errorMessage);
		}
		wizard.finish();
		return this;
	}

	/**
	 * Checks whether JRE location is OK or not. This is achieved by opening
	 * "Add JRE" wizard, filling up fields and looking for errors. If there is
	 * no error, null is returned. Error message otherwise.
	 *
	 * @param path the path
	 * @param name the name
	 * @return null if everything is ok. Error message otherwise.
	 */

	public String validateJRELocation(String path, String name) {
		WizardDialog wizard = openAddJREWizard();
		StandardVMPage vmPage = fillJREDialog(wizard, path, name);
		if (!wizard.isFinishEnabled()) {
			String errorMessage = vmPage.getErrorMessage();
			wizard.cancel();
			return errorMessage;
		}
		wizard.cancel();
		return null;
	}

	/**
	 * Returns list of table items containing configured JREs.
	 *
	 * @return the JR es
	 */

	public List<JREItem> getJREs() {
		ArrayList<JREItem> resultList = new ArrayList<JREItem>();
		DefaultTable table = new DefaultTable(this);
		for (TableItem item : table.getItems()) {
			resultList.add(new JREItem(item.getText(0), item.getText(1), item.getText(2)));
		}
		return resultList;
	}

	/**
	 * Deletes given JRE.
	 * 
	 * @param name
	 *            Name of JRE to be deleted.
	 */

	public JREsPreferencePage deleteJRE(String name) {
		selectJRE(name);
		new PushButton(referencedComposite, "Remove").click();
		return this;
	}
	
	/**
	 * Set given JRE to default by toggling checkbox on.
	 * @return JREsPreferencePage object
	 */
	public JREsPreferencePage toggleJRE(String name, boolean toggle) {
		getJRETableItem(name).setChecked(toggle);
		// we need to check if at least one java is chosen, otherwise apply is disabled
		// and error is shown
		if (!(new PushButton(referencedComposite, "Apply").isEnabled())) {
			throw new EclipseLayerException("At least one valid JRE must be chosen - expected default JRE for workspace");
		}
		return this;
	}
	
	/**
	 * Selects given JRE.
	 * @param name JRE name in string
	 * @return
	 */
	public JREsPreferencePage selectJRE(String name) {
		getJRETableItem(name).select();
		return this;
	}
	
	/**
	 * Return existing JRE.
	 * @param name jre's name
	 * @return existing JRE item object in table
	 */
	public TableItem getJRETableItem(String name) {
		DefaultTable table = new DefaultTable(this);
		try {
			return table.getItem(name);
		} catch (CoreLayerException exc) {
			// if set to default, name is changed to "${name} (default)"
			return table.getItem(name + " (default)");
		}
	}
	
	/**
	 * Return existing JRE.
	 * @param name jre's name
	 * @return existing JRE item object in table
	 */
	public JREItem getJREItem(String name) {
		TableItem item = getJRETableItem(name);
		return new JREItem(item.getText(0), item.getText(1), item.getText(2), item.isChecked());
	}
	
	/**
	 * Toggle given JRE on and sets it to default.
	 * @param name JRE name to search for
	 * @return JREsPreferencePage object
	 */
	public JREsPreferencePage setDefaultJRE(String name) {
		return toggleJRE(name, true);
	}

	private AddVMInstallWizard openAddJREWizard() {
		new PushButton(this, "Add...").click();
		AddVMInstallWizard wizard = new AddVMInstallWizard();
		new VMTypePage(wizard).selectType("Standard VM");
		wizard.next();
		return wizard;
	}

	private StandardVMPage fillJREDialog(WizardDialog wizard, String path, String name) {
		StandardVMPage standardVMPage = new StandardVMPage(wizard);
		standardVMPage.setJREHome(path);
		if (name != null) {
			standardVMPage.setName(name);
		}
		return standardVMPage;
	}
}
