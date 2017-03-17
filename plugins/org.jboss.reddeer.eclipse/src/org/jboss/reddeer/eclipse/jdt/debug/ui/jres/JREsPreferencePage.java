/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.jdt.debug.ui.jres;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.preferences.JREItem;
import org.jboss.reddeer.eclipse.jdt.ui.preferences.internal.StandardVMPage;
import org.jboss.reddeer.eclipse.jdt.ui.preferences.internal.VMTypePage;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.api.TableItem;
import org.jboss.reddeer.swt.impl.button.FinishButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.table.DefaultTable;

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
	public JREsPreferencePage() {
		super(new String[] { "Java", "Installed JREs" });
	}

	/**
	 * Adds JRE.
	 * 
	 * @param path
	 *            path to JRE.
	 * @throws EclipseLayerException
	 *             if Add JRE wizard cannot be completed.
	 */

	public void addJRE(String path) {
		addJRE(path, null);
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

	public void addJRE(String path, String name) {
		WizardDialog wizard = openAddJREWizard();
		StandardVMPage vmPage = fillJREDialog(path, name);
		if (!new FinishButton().isEnabled()) {
			String errorMessage = vmPage.getErrorMessage();
			wizard.cancel();
			throw new EclipseLayerException(errorMessage);
		}
		wizard.finish();
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
		StandardVMPage vmPage = fillJREDialog(path, name);
		if (!new FinishButton().isEnabled()) {
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
		DefaultTable table = new DefaultTable();
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

	public void deleteJRE(String name) {
		DefaultTable table = new DefaultTable();
		table.getItem(name, 0).select();
		new PushButton("Remove").click();
	}

	private WizardDialog openAddJREWizard() {
		new PushButton("Add...").click();
		WizardDialog wizard = new WizardDialog();
		new VMTypePage().selectType("Standard VM");
		wizard.next();
		return wizard;
	}

	private StandardVMPage fillJREDialog(String path, String name) {
		StandardVMPage standardVMPage = new StandardVMPage();
		standardVMPage.setJREHome(path);
		if (name != null) {
			standardVMPage.setName(name);
		}
		return standardVMPage;
	}
}
