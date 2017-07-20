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
package org.eclipse.reddeer.eclipse.jdt.debug.ui.jres;

import java.util.ArrayList;
import java.util.List;

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
 * @author rhopp
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
		DefaultTable table = new DefaultTable(referencedComposite);
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
		DefaultTable table = new DefaultTable(referencedComposite);
		table.getItem(name, 0).select();
		new PushButton(referencedComposite, "Remove").click();
		return this;
	}

	private AddVMInstallWizard openAddJREWizard() {
		new PushButton(referencedComposite, "Add...").click();
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
